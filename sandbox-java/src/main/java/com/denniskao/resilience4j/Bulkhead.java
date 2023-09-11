package com.denniskao.resilience4j;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import java.time.Duration;

public class Bulkhead {

  public static void main(String[] args) {

    BulkheadConfig config = BulkheadConfig.custom()
        .maxConcurrentCalls(100)
        .maxWaitDuration(Duration.ofMillis(100))
        .build();

  }
}
