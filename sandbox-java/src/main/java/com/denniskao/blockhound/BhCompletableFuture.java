package com.denniskao.blockhound;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ReactiveAdapterRegistry.SpringCoreBlockHoundIntegration;
import reactor.blockhound.BlockHound;

public class BhCompletableFuture {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {

    BlockHound.builder()
        .nonBlockingThreadPredicate(current ->
            current.or(thread -> thread.getName().equals("main"))
        )
        // writing to IO is a blocking operation, allow print to avoid printing another stacktrace
        .allowBlockingCallsInside("java.io.PrintStream", "println")
        // print stacktrace without throwing exception
        .blockingMethodCallback(blockingMethod ->
            new Exception(blockingMethod.toString()).printStackTrace()
        )
        .install();

    var future = new CompletableFuture<Void>();
    try {
      future.get(1, TimeUnit.SECONDS);
    } catch (TimeoutException _){
    }
    System.out.println("done");
  }
}
