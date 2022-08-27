package com.denniskao;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JdkServer {
    public static void main(String[] args) throws IOException {
        var server = HttpServer
                .create()
                .createContext("/", exchange -> {
                    exchange.getResponseHeaders().add("Accept-CH", "Sec-CH-UA-Arch");
                    exchange.sendResponseHeaders(200, 2);
                    try (var out = exchange.getResponseBody()) {
                        out.write("ok".getBytes());
                    }
                    exchange.close();
                })
                .getServer();

        server.bind(new InetSocketAddress(8080), 0);
        server.start();

    }
}
