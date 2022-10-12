package com.denniskao.timeouts;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class FutureChain {
    public static void main(String[] args) throws InterruptedException {
        var latch = new CountDownLatch(1);
        var job1 = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("start");
                latch.await();
                System.out.println("done");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var job2 = job1.whenComplete((res, ex) -> {
            System.out.println("applyAsync: " + Thread.currentThread().getName() + " " + ex);
        });
        job1.cancel(true);
        Thread.sleep(1000);
    }
}
