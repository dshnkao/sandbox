package com.denniskao.classloading;

/**
 * Shows that Class is only loaded and initialized when referenced.
 * And by the thread that referenced the class.
 */
public class ClassLoading {
  static class Foo {
    static {
      System.out.println(Thread.currentThread().getName());
    }
  }

  public static void main(String[] args) {
    System.out.println("before");
    var lock = new Foo();
    System.out.println("after");
  }
}
