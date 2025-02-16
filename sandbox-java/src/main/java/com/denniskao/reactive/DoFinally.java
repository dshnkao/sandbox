package com.denniskao.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class DoFinally {
    public static void main(String[] args) throws InterruptedException {
        var publisher = Mono.just("data")
                .doOnSubscribe(data -> {
                    System.out.println("doOnSubscribe: " + Thread.currentThread());
                })
                .doOnSuccess(data -> {
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("doOnSuccess:" + Thread.currentThread());
                })
                .doFinally(data -> {
                    System.out.println("doFinally:" + Thread.currentThread());
                })
                .subscribeOn(Schedulers.parallel())
                .subscribe(data -> System.out.println(data + Thread.currentThread()));
        Thread.sleep(2000);
    }
}
