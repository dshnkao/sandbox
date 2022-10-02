package com.denniskao;

import com.google.common.base.Stopwatch;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Loom {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var stopwatch = Stopwatch.createStarted();
        // virtual thread
        System.out.println(stopwatch.elapsed() + ":start");

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits
        System.out.println(stopwatch.elapsed() + ":end");

        // structured concurrency
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var foo  = scope.fork(Loom::findFoo);
            var bar = scope.fork(Loom::findBar);

            scope.join();           // Join both forks
            scope.throwIfFailed();  // ... and propagate errors

            // Here, both forks have succeeded, so compose their results
            System.out.println(foo.get() + bar.get());
        }
        System.out.println(stopwatch.elapsed() + ":end");
    }

    static String findFoo() throws InterruptedException {
        Thread.sleep(5000);
        latch.countDown();
        return "foo";
    }

    static String findBar() throws InterruptedException {
        latch.await();
        return "bar";
    }
}
