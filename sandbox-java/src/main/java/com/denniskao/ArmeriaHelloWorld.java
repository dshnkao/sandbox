package com.denniskao;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;

public class ArmeriaHelloWorld {
    public static void main(String[] args) {

        var sb = Server.builder();
        Server server = sb
                .http(8080)
                .service("/", (ctx, req) -> HttpResponse.of("hello"))
                .build();
        server.start().join();
    }
}
