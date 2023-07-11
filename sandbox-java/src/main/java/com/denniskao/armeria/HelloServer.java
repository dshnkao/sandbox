package com.denniskao.armeria;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;

public class HelloServer {
  public static void main(String[] args) {
    var sb = Server
        .builder()
        .http(8080)
//        .virtualHost("")
        .decorator("/", service -> {
          System.out.println("decorate");
          return service;
        })
        .service("/", (ctx, req) -> HttpResponse.of("hello"));
    var server = sb.build();
    server.start().join();
  }
}
