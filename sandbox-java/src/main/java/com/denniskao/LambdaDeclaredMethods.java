package com.denniskao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Shows that lambdas are generated as static methods
 */
public class LambdaDeclaredMethods {
    private static final ExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {

        for (var i = 0; i < 10; i++) {
            executor.submit(() -> {
            });
            executor.submit(() -> {
            });
        }
        Supplier<Void> x = () -> null;
        Function<Object, Object> y = (executor) -> null;

        Stream.of(LambdaDeclaredMethods.class.getDeclaredMethods()).forEach(System.out::println);

        executor.shutdown();
    }
}
