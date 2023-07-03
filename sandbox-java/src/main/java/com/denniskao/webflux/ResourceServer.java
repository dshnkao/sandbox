package com.denniskao.webflux;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class ResourceServer {

  static class Handler {
    public Mono<ServerResponse> getFile(ServerRequest request) {
      return ServerResponse.ok().body("", String.class);
    }
  }

  public static void main(String[] args) {
    var handler = new Handler();

    var fs = new FileSystemResource("/Users/denniskao/repos/");

    RouterFunction<ServerResponse> route =
        RouterFunctions.route() //
            .resources("/resource/**", fs)
            .GET("/file", handler::getFile)
            .build();

    HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
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
