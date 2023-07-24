package com.denniskao.reactive;

import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

public class SinkExample {
    public static void main(String[] args) {
        Sinks.One<String> s = Sinks.one();
        var m = s.asMono();
        m.subscribeOn(Schedulers.boundedElastic()).subscribe(System.out::println);

        s.emitValue("lol", Sinks.EmitFailureHandler.FAIL_FAST);

    }
}
