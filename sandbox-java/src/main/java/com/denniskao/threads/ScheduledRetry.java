package com.denniskao.threads;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledRetry {

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public static void main(String[] args) {
        retry();
    }


    private static void retry() {
        Runnable task = () -> {
            try {
                System.out.println("hello");
                if (true) {
                    throw new Exception("throw");
                }
            } catch (Exception e) {
                retry();
            }
        };

        executor.schedule(task, 5, TimeUnit.MICROSECONDS);
    }
}
