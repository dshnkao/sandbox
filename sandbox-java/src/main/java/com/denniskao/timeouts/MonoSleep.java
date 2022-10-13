package com.denniskao.timeouts;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class MonoSleep {
    public static void main(String[] args) {
        var job = Mono.fromCallable(() -> {
                    System.out.println("start");
                    Thread.sleep(1000);
                    System.out.println("end");
                    return "foo";
                })
                .delayElement(Duration.ofMillis(1000))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
        job.dispose();
    }
}
