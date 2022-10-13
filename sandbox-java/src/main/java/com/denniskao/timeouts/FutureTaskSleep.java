package com.denniskao.timeouts;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FutureTaskSleep {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();
        var job = executor.submit(() -> {});
        job.get();
    }
}
