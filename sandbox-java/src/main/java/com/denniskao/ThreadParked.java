package com.denniskao;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class ThreadParked {
    private static final CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args){
        var executor = Executors.newSingleThreadExecutor();
        var wait = CompletableFuture.runAsync(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executor);
        var countdown = CompletableFuture.runAsync(latch::countDown, executor);
        countdown.join();
        // thread is blocked forever
        // JFR shows thread is "parked"
        // JFR shows thread isn't blocked on monitor (waiting for a lock)
        System.out.println("done");
    }
}
