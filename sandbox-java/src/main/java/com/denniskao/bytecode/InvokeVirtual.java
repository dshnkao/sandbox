package com.denniskao.bytecode;

public class InvokeVirtual {

  static class Foo {
    final void run() {}
  }

  public static void main(String[] args) {
    var foo = new Foo();
    foo.run();
  }
}
