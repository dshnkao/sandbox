package com.denniskao.timeouts;

import java.util.concurrent.CompletableFuture;

public class CFuture {
    public static void main(String[] args) throws InterruptedException {
        var job = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("start");
                Thread.sleep(1000);
                System.out.println("done");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        job.cancel(true);
        Thread.sleep(2000);
    }
}
