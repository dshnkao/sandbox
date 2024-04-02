package com.denniskao.webflux;

import com.google.common.net.HttpHeaders;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.time.Duration;
import java.util.List;

public class FluxResponse {
    static class Handler {
        public Mono<ServerResponse> mono(ServerRequest request) {
            return ServerResponse.ok()
                    .header("key1", "val1")
                    .bodyValue("ok");
        }

        public Mono<ServerResponse> flux(ServerRequest request) {

            var first = Mono.just("0");
            var body = Flux.interval(Duration.ofSeconds(2)).map(String::valueOf).take(Duration.ofSeconds(20));
            var all = Flux.concat(first, body);

            return ServerResponse.ok()
                    .header("key", "val1")
                    .contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(all, String.class);
        }
    }

    public static void main(String[] args) {
        var handler = new Handler();

        var fs = new FileSystemResource("/Users/denniskao/repos/");

        RouterFunction<ServerResponse> canvaRoutes =
                RouterFunctions.route() //
                        .resources("/resource/**", fs)
                        .GET("/mono", handler::mono)
                        .GET("/flux", handler::flux)
                        .build();


        HttpHandler httpHandler = RouterFunctions.toHttpHandler(canvaRoutes);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer.create()
                .host("localhost")
                .port(9089)
                .handle(adapter)
                .accessLog(true)
                .bindNow()
                .onDispose()
                .block();
    }
}

