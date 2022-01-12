package com.denniskao;

import io.netty.util.internal.ConcurrentSet;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ReactorSubSet {
    public static void main(String[] args) throws InterruptedException {
        var subs = ConcurrentHashMap.<AtomicReference<Subscription>>newKeySet();
        var ref = new AtomicReference<Subscription>();
        for (int i = 0; i < 10; i++) {
            Flux.interval(Duration.ofMillis(200))
                    .doOnSubscribe(s -> {
                        ref.set(s);
                        subs.add(ref);
                        System.out.println("next: " + Arrays.toString(subs.toArray()));
                    })
                    .doFinally(s -> {
                        subs.remove(ref);
                        System.out.println("finally");
                    })
                    .takeUntil(x -> x == 10)
                    .subscribe();
        }

        Thread.sleep(2500);
        System.out.println("next: " + Arrays.toString(subs.toArray()));
    }
}
