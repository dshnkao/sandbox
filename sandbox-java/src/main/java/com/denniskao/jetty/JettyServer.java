package com.denniskao.jetty;

import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    public static class OkWriteListener implements WriteListener {
        private final AsyncContext asyncContext;
        private final ServletOutputStream outputStream;
        private final byte[] buffer = new byte[1_000_000_000];
        volatile int offset = 0;

        public OkWriteListener(AsyncContext asyncContext, ServletOutputStream outputStream) {
            this.asyncContext = asyncContext;
            this.outputStream = outputStream;
            Arrays.fill(buffer, (byte) 1);
        }

        @Override
        public void onWritePossible() throws IOException {
            logger.info(Thread.currentThread().getName());
            if (offset >= 1_000_000_000) {
                asyncContext.complete();
            }
            while (outputStream.isReady()) {
                logger.info("ready {}", offset);
                outputStream.write(buffer, offset, 1);
                offset += 1;
                outputStream.write(buffer);
            }
            logger.info("not ready {}", offset);
        }

        @Override
        public void onError(Throwable t) {

        }
    }

    public static class OkServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) {
            try {

                System.out.println(request.getServerName());
                var ctx = request.startAsync();
                var out = response.getOutputStream();
                out.setWriteListener(new OkWriteListener(ctx, out));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        var server = new Server();
        var config = new HttpConfiguration();
        var http2 = new HTTP2CServerConnectionFactory(config);
        var connector = new ServerConnector(server, 1, 1, http2);
        connector.setPort(6000);

        server.addConnector(connector);

        var ctx = new ServletContextHandler();
        ctx.setContextPath("/");
//        ctx.setVirtualHosts(new String[]{"aaa.a.a"});

        var ok = new ServletHolder(new OkServlet());
        ok.setAsyncSupported(true);
        ctx.addServlet(ok, "/ok");

        server.setHandler(ctx);
        server.start();
    }
}
