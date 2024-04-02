package com.denniskao.webflux;

import com.google.common.net.HttpHeaders;

import java.util.List;
import java.util.Objects;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class RouterFunctionServer {

  static class Handler {
    public Mono<ServerResponse> mono(ServerRequest request) {
      return ServerResponse.ok().bodyValue("ok");
    }

    public Mono<ServerResponse> flux(ServerRequest request) {
      return ServerResponse.ok().body(Flux.fromIterable(List.of("1", "2", "3", "4")), String.class);
    }
  }

  public static void main(String[] args) {
    var handler = new Handler();

    var fs = new FileSystemResource("/Users/denniskao/repos/");

    RouterFunction<ServerResponse> canvaRoutes =
        RouterFunctions.route() //
            .resources("/resource/**", fs)
            .GET("/ok", handler::mono)
            .build();

    // an attempt at virtual hosting
    var hostPredicate = RequestPredicates.headers(
        headers -> {
          var xForwardedHost = headers.firstHeader(HttpHeaders.X_FORWARDED_HOST);
          var host = headers.firstHeader(HttpHeaders.HOST);
          if ("www.canva.com".equals(xForwardedHost)) {
            return true;
          } else
            return "www.canva.com".equals(host);
        });

    var route = RouterFunctions.nest(hostPredicate, canvaRoutes);

    HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
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
