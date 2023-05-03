package com.denniskao;

import java.util.concurrent.CountDownLatch;

public class StaticLoader {
  static class Foo {
    static {
      System.out.println(Thread.currentThread().getName());
    }
  }

  public static void main(String[] args) {
    var lock = new Foo();

    var countDownLatch = new CountDownLatch(1);
    var job = new Thread(() -> {
      try {
        countDownLatch.await();
      } catch (InterruptedException e) {
      }
    });
    job.start();
    job.interrupt();
    Thread.currentThread().interrupt();
  }


}
