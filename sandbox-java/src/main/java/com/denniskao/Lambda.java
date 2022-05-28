package com.denniskao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Lambda {
    private static final ExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {


        for (var i = 0; i < 10; i++) {
            executor.submit(() -> {
            });
            executor.submit(() -> {
            });
        }
        Stream.of(Lambda.class.getDeclaredMethods()).forEach(System.out::println);
        executor.shutdown();
    }
}
