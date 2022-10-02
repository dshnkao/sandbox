package com.denniskao;

import java.util.concurrent.*;

public class ThreadPoolHandle {
  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    var latch = new CountDownLatch(1);
    CompletableFuture.runAsync(
            () -> {
              try {
                latch.await();
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            })
        .handleAsync(
            (res, ex) -> {
              System.out.println("handle async");
              return res;
            })
        // .orTimeout(5000, TimeUnit.MILLISECONDS)
        .exceptionally(
            (ex) -> {
              System.out.println("exceptionally");
              return null;
            })
        .get(1000, TimeUnit.MILLISECONDS);
  }
}
