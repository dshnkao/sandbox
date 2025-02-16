package com.denniskao.jetty;

import com.twitter.finagle.Http;
import com.twitter.finagle.http.Method;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http2.param.PriorKnowledge;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * Finagle Client <---HTTP/2---> Jetty Server
 * {@link HttpServletRequest#getServerName()} returns different for curl and Finagle client
 * curl --http2-prior-knowledge -i localhost:6000 -H Host:www.canva.com
 * prints www.canva.com
 * Finagle Client
 * prints IP address
 */
public class JettyH2ServerNameIssue {
    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    private static int PORT = 6000;

    public static class OkServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) {
            try {
                var serverName = request.getServerName();
                var respBody = "getServerName: " + serverName + " | "
                        + "getHeader: " + request.getHeader("Host") + "\n";
                logger.info("Headers: " + Collections.list(request.getHeaderNames()));
                logger.info(respBody);
                var out = response.getOutputStream();
                out.write(respBody.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        startJetty();
        startFinagleClient();
    }

    static void startJetty() throws Exception {
        var server = new Server();
        var config = new HttpConfiguration();
        var http1 = new HttpConnectionFactory(config);
        // HTTP2 connection only
        var http2 = new HTTP2CServerConnectionFactory(config);
        var alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol(http2.getProtocol());
        var sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath("/Users/dennis.k/work/canva-pomgen/keystore.jks");
        sslContextFactory.setKeyStorePassword("qqqqqq");
        var ssl = new SslConnectionFactory(sslContextFactory, alpn.getProtocol());
        var connector = new ServerConnector(server, 1, 1, ssl, alpn, http2, http1);
        SecureRequestCustomizer src = new SecureRequestCustomizer();
        src.setSniHostCheck(false);
        config.addCustomizer(src);
        connector.setPort(6000);
        server.addConnector(connector);
        var ctx = new ServletContextHandler();
        var ok = new ServletHolder(new OkServlet());
        ok.setAsyncSupported(true);
        ctx.addServlet(ok, "");
        server.setHandler(ctx);
        server.start();
    }

    static void startFinagleClient() throws InterruptedException {
        var client = Http.client()
                .withLabel("my-http-client")
                .withHttp2()
                .withTlsWithoutValidation()
                .configured(new PriorKnowledge(true).mk()) //
                .newService("localhost:" + PORT);

        var req = Request.apply("");
        req.method(Method.Post());
        req.headerMap().put("Host", "www.canva.com");
        req.headerMap().put("host", "foo.com");
        req.headerMap().put("Test", "test");
//        req.host("www.canva.com");
        client.apply(req);
    }
}
