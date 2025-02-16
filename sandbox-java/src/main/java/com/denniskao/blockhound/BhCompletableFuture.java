package com.denniskao.blockhound;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import reactor.blockhound.BlockHound;

public class BhCompletableFuture {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {

    BlockHound.builder()
        .nonBlockingThreadPredicate(current ->
            current.or(thread -> thread.getName().equals("main"))
        )
        .install();

    var future = new CompletableFuture<Void>();
    future.get(5, TimeUnit.SECONDS);
  }
}
