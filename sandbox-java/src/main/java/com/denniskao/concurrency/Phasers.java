package com.denniskao.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

public class Phasers {
    public static void main(String[] args) {
        var phaser = new Phaser(1);

        for (var i = 0; i < 3; i++) {
            phaser.register();
            CompletableFuture.runAsync(() -> {
                var jitter = ThreadLocalRandom.current().nextInt(1000, 2000);
                try {
                    Thread.sleep(jitter);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": arrived and waiting");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + ": proceed");
            });
        }

        System.out.println(Thread.currentThread().getName() + ": start");
        phaser.arriveAndAwaitAdvance();
//        phaser.arriveAndDeregister();
        System.out.println(Thread.currentThread().getName() + ": end");
    }
}
