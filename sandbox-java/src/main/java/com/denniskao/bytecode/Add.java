package com.denniskao.bytecode;

public class Add {
  public static void main(String[] args) {
    int x = 10;
    int y = 20;

    int z = x + y; // bytecode instructions

    // Compiled from "Add.java"
    // public class com.denniskao.bytecode.Add {
    //  public com.denniskao.bytecode.Add();
    //    Code:
    //       0: aload_0
    //       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
    //       4: return
    //
    //  public static void main(java.lang.String[]);
    //    Code:
    //       0: bipush        10
    //       2: istore_1
    //       3: bipush        20
    //       5: istore_2
    //       6: iload_1
    //       7: iload_2
    //       8: iadd
    //       9: istore_3
    //      10: return
    // }
  }
}
