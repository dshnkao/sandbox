package com.denniskao.reactive;

import reactor.core.publisher.Flux;

public class ErrorSignal {
    public static void main(String[] args) {
        var publisher = Flux.just("a", "b", "c", "d")
                .map(data -> {
                    if (data.equals("b")) {
                        throw new RuntimeException("error");
                    }
                    System.out.println(data);
                    return data;
                }).log();
        publisher.subscribe();
    }
}
