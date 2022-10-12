package com.denniskao.timeouts;

import java.util.concurrent.CountDownLatch;

public class InterruptLatch {
    public static void main(String[] args) {
        var latch = new CountDownLatch(0);
        var job = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        job.start();
        job.interrupt();
    }
}
