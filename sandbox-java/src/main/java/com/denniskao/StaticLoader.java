package com.denniskao;

public class StaticLoader {
  static class Foo {
    static {
      System.out.println(Thread.currentThread().getName());
    }
  }

  public static void main(String[] args) {
    new Foo();
  }
}
