package com.denniskao.webflux;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

public class ControllerServer {

    @EnableWebFlux
    @Controller
    static class FooController {
        @RequestMapping("/foo")
        public ResponseEntity<String> getFoo() {
            return ResponseEntity.ok("ok");
        }
    }
    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(FooController.class);
        ctx.refresh();
        var httpHandler = WebHttpHandlerBuilder.applicationContext(ctx).build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer.create()
                .host("localhost")
                .port(9080)
                .handle(adapter)
                .accessLog(true)
                .bindNow()
                .onDispose()
                .block();
    }
}
