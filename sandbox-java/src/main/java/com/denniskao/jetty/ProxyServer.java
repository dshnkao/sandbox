package com.denniskao.jetty;

import org.eclipse.jetty.proxy.AsyncProxyServlet;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ProxyServer {
    public static void main(String[] args) throws Exception {
        var server = new Server();
        var connector = new ServerConnector(server, 1, 1, new HttpConnectionFactory());
        connector.setPort(8080);
        server.addConnector(connector);

        var ctx = new ServletContextHandler();
        ctx.setContextPath("/");

        var proxy = new AsyncProxyServlet.Transparent();
        var ok = new ServletHolder(proxy);
        ok.setInitParameter("proxyTo", "http://192.168.1.15:9000");

        ok.setAsyncSupported(true);
        ctx.addServlet(ok, "/");

        server.setHandler(ctx);
        server.start();
    }
}
