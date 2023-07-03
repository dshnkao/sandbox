package com.denniskao.threads;

public class ThreadLocalAndScopedValue {

  final static ThreadLocal<String> LOCAL = new ThreadLocal<>();
  final static ScopedValue<String> SCOPED = ScopedValue.newInstance();

  public static void main(String[] args) {
    LOCAL.set("abc");

    ScopedValue.where(SCOPED, "abc")                            // (2)
        .run(() -> System.out.println(SCOPED.get()));
  }
}
