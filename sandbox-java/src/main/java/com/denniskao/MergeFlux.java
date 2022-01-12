package com.denniskao;

import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class MergeFlux {
    public static void main(String[] args) throws InterruptedException {
        case2();
    }

    static void case1() throws InterruptedException {
        Sinks.Many<Long> sink = Sinks.many().multicast().directBestEffort();
        var xs = Flux.interval(Duration.ofSeconds(1))
                .mergeWith(sink.asFlux())
                .takeUntil(x -> x == 10);

        var ys = Flux.interval(Duration.ofSeconds(1))
                .mergeWith(sink.asFlux())
                .takeUntil(x -> x == 20);

        xs.subscribeOn(Schedulers.boundedElastic())
                .subscribe(x -> System.out.println("x: " + x));
        ys.subscribeOn(Schedulers.boundedElastic())
                .subscribe(y -> System.out.println("y: " + y));

        Thread.sleep(3000);
        sink.emitNext(10L, Sinks.EmitFailureHandler.FAIL_FAST);
        Thread.sleep(3000);
        sink.emitNext(20L, Sinks.EmitFailureHandler.FAIL_FAST);
        Thread.sleep(10000);
    }

    static void case2() throws InterruptedException {
        Sinks.Many<Integer> sink = Sinks.many().multicast().directBestEffort();
        AtomicReference<Subscription> sub = new AtomicReference<>();
        var flux1 = Flux.range(0, 5).delayElements(Duration.ofMillis(200));
        var merged1 = flux1
                .mergeWith(sink.asFlux().takeUntilOther(flux1.then()))
                .doOnNext((v) -> System.out.println("next1: " + v))
                .doOnComplete(() -> System.out.println("merged1: complete"))
                .subscribe();

        var flux2 = Flux.range(0, 10).delayElements(Duration.ofMillis(200));
        var merged2 = flux2.mergeWith(sink.asFlux().takeUntilOther(flux2.then()))
                .doOnNext((v) -> System.out.println("next2: " + v))
                .doOnComplete(() -> System.out.println("merged2: complete"))
                .subscribe();

        Thread.sleep(2000L);
        merged2.dispose();
        System.out.println(sink.tryEmitNext(500));
        Thread.sleep(2500L);
    }
}
