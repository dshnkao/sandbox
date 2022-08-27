package com.denniskao;

import java.util.concurrent.*;

public class ThreadPoolQueue {
    public static void main(String[] args) {
        var executor = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
        Runnable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + ": started");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + ": ended");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        for (var i = 0; i < 10; i++) {
            executor.execute(runnable);
        }
    }
}
