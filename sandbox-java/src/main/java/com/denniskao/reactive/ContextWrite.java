package com.denniskao.reactive;

import java.time.Duration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class ContextWrite {
  public static void main(String[] args) {
    var blocked =
        Flux.deferContextual(
                ctx -> {
                  System.out.println("thread: " + Thread.currentThread().getName());
                  System.out.println("ctx: " + ctx);
                  return Flux.interval(Duration.ofSeconds(1));
                })
            .contextWrite(Context.of("key1", "val1"))
            .doOnEach(
                signal -> {
                  System.out.println("thread: " + Thread.currentThread().getName());
                  System.out.println("sig: " + signal.get());
                  System.out.println("ctx: " + signal.getContextView());
                })
            .contextWrite(Context.of("key2", "val2"))
            .blockLast();
//    System.out.println(blocked);
  }
}
