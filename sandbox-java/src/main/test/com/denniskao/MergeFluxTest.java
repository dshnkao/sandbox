package com.denniskao;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MergeFluxTest {

    @Test
    void mergeFlux() {

        var one = Flux.fromStream(Stream.of(1,1));
        var two = Flux.fromStream(Stream.of(2,2,2,2,2));
        var merged = one.mergeWith(two);

        StepVerifier.create(merged)
                .expectNextMatches(x -> true)
                .expectNextMatches(x -> true)
                .expectNextMatches(x -> true)
                .verifyComplete();


    }
}