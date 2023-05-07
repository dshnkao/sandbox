package com.denniskao.jetty;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.QoSFilter;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.thread.MonitoredQueuedThreadPool;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.io.IOException;
import java.util.EnumSet;
import java.util.concurrent.BlockingQueue;

public class JettyQoS {
    static class OkServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) {
            try {
                if (!request.getRequestURI().startsWith("/healthz")) {
                    Thread.sleep(100);
                }
                response.getOutputStream().write("ok".getBytes());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class CustomQoSFilter extends QoSFilter {

        private final BlockingQueue<Runnable> queue;

        CustomQoSFilter(BlockingQueue<Runnable> queue) {
            this.queue = queue;
        }


        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("queue:" + queue.size());
            super.doFilter(request, response, chain);
        }

        @Override
        protected int getPriority(ServletRequest request) {
            var req = (HttpServletRequest) request;
            if (req.getRequestURI().startsWith("/healthz")) {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        var queue = new BlockingArrayQueue<Runnable>(100);
        var pool = new MonitoredQueuedThreadPool(4, 4, 60, queue);
        var server = new Server(pool);
        var connector = new ServerConnector(server, 1, 1, new HttpConnectionFactory());
        connector.setPort(8080);
        server.addConnector(connector);
        var ctx = new ServletContextHandler();
        ctx.setContextPath("/");

        var qosFilter = new CustomQoSFilter(queue);
        var qos = new FilterHolder(qosFilter);

        var ok = new ServletHolder(new OkServlet());
        ok.setAsyncSupported(true);
        ctx.addServlet(ok, "/*");
        ctx.addFilter(qos, "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC));

        server.setHandler(ctx);
        server.start();
    }
}
