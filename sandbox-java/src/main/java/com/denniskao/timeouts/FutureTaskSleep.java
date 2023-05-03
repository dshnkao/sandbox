package com.denniskao.timeouts;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTaskSleep {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var executor = Executors.newSingleThreadExecutor();
    Future<?> job =
        executor.submit(
            () -> {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            });
    job.cancel(true);
    job.get();
  }
}
