package com.denniskao.timeouts;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTimeout {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var j = CompletableFuture.completedFuture(1);
    j.thenApply(
        ignored -> {
          System.out.println("abc");
          return null;
        });

    var job =
        CompletableFuture.runAsync(
                () -> {
                  System.out.println(Thread.currentThread().getName());
                  for (var i = 0; i <= 2; i++) {
                    System.out.println("while");
                    try {
                      Thread.sleep(1000);
                    } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                    }
                  }
                })
            .thenApply(
                ignored -> {
                  System.out.println("2");
                  return null;
                })
            .orTimeout(100, TimeUnit.MILLISECONDS)
            .exceptionally(
                ex -> {
                  try {
                    Thread.sleep(100);
                  } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                  }
                  System.out.println(Thread.currentThread().getName());
                  return null;
                });
    job.get();
    System.out.println(Thread.currentThread().getName());
    Thread.sleep(6000);

    Runnable doFoo = () -> {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };
    var job3 = CompletableFuture.runAsync(doFoo)
            .thenRunAsync(() -> {});
    job3.cancel(true);
  }
}
