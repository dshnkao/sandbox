package com.denniskao;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <a href="https://openjdk.org/jeps/371">JEP 371: Hidden Class</a>
 *
 * <p>classes that cannot be used directly by the bytecode of other classes. Hidden classes are
 * intended for use by frameworks that generate classes at run time and use them indirectly, via
 * reflection
 */
public class HiddenClass {

  public static class Foo {
    public String convertToUpperCase(String s) {
      return s.toUpperCase();
    }
  }

  public static void main(String[] args)
      throws IOException,
          IllegalAccessException,
          NoSuchMethodException,
          InvocationTargetException,
          InstantiationException,
          ClassNotFoundException {
    var lookup = MethodHandles.lookup();
    // var clazz = Foo.class;
    // var className = clazz.getName();
    // var classAsPath = className.replace('.', '/') + ".class";
    // var stream = clazz.getClassLoader().getResourceAsStream(classAsPath);
    // var fooBytes = stream.readAllBytes();
    // System.out.println(Arrays.toString(fooBytes));
    byte[] fooBytes = {
      -54, -2, -70, -66, 0, 0, 0, 61, 0, 31, 10, 0, 2, 0, 3, 7, 0, 4, 12, 0, 5, 0, 6, 1, 0, 16, 106,
      97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 1, 0, 6, 60, 105, 110, 105,
      116, 62, 1, 0, 3, 40, 41, 86, 10, 0, 8, 0, 9, 7, 0, 10, 12, 0, 11, 0, 12, 1, 0, 16, 106, 97,
      118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 1, 0, 11, 116, 111, 85, 112,
      112, 101, 114, 67, 97, 115, 101, 1, 0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110,
      103, 47, 83, 116, 114, 105, 110, 103, 59, 7, 0, 14, 1, 0, 29, 99, 111, 109, 47, 100, 101, 110,
      110, 105, 115, 107, 97, 111, 47, 72, 105, 100, 100, 101, 110, 67, 108, 97, 115, 115, 36, 70,
      111, 111, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114,
      84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84,
      97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 31, 76, 99, 111, 109, 47, 100, 101, 110,
      110, 105, 115, 107, 97, 111, 47, 72, 105, 100, 100, 101, 110, 67, 108, 97, 115, 115, 36, 70,
      111, 111, 59, 1, 0, 18, 99, 111, 110, 118, 101, 114, 116, 84, 111, 85, 112, 112, 101, 114, 67,
      97, 115, 101, 1, 0, 38, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114,
      105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105,
      110, 103, 59, 1, 0, 1, 115, 1, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83,
      116, 114, 105, 110, 103, 59, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0,
      16, 72, 105, 100, 100, 101, 110, 67, 108, 97, 115, 115, 46, 106, 97, 118, 97, 1, 0, 8, 78,
      101, 115, 116, 72, 111, 115, 116, 7, 0, 28, 1, 0, 25, 99, 111, 109, 47, 100, 101, 110, 110,
      105, 115, 107, 97, 111, 47, 72, 105, 100, 100, 101, 110, 67, 108, 97, 115, 115, 1, 0, 12, 73,
      110, 110, 101, 114, 67, 108, 97, 115, 115, 101, 115, 1, 0, 3, 70, 111, 111, 0, 33, 0, 13, 0,
      2, 0, 0, 0, 0, 0, 2, 0, 1, 0, 5, 0, 6, 0, 1, 0, 15, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42,
      -73, 0, 1, -79, 0, 0, 0, 2, 0, 16, 0, 0, 0, 6, 0, 1, 0, 0, 0, 12, 0, 17, 0, 0, 0, 12, 0, 1, 0,
      0, 0, 5, 0, 18, 0, 19, 0, 0, 0, 1, 0, 20, 0, 21, 0, 1, 0, 15, 0, 0, 0, 57, 0, 1, 0, 2, 0, 0,
      0, 5, 43, -74, 0, 7, -80, 0, 0, 0, 2, 0, 16, 0, 0, 0, 6, 0, 1, 0, 0, 0, 14, 0, 17, 0, 0, 0,
      22, 0, 2, 0, 0, 0, 5, 0, 18, 0, 19, 0, 0, 0, 0, 0, 5, 0, 22, 0, 23, 0, 1, 0, 3, 0, 24, 0, 0,
      0, 2, 0, 25, 0, 26, 0, 0, 0, 2, 0, 27, 0, 29, 0, 0, 0, 10, 0, 1, 0, 13, 0, 27, 0, 30, 0, 9
    };

    // hidden
    var hiddenClass =
        lookup
            .defineHiddenClass(fooBytes, true, MethodHandles.Lookup.ClassOption.NESTMATE)
            .lookupClass();

    var hiddenClassObject = hiddenClass.getConstructor().newInstance();

    var hiddenClassMethod =
        hiddenClassObject.getClass().getDeclaredMethod("convertToUpperCase", String.class);
    var hiddenOut = hiddenClassMethod.invoke(hiddenClassObject, "Hello");
    System.out.println(hiddenOut);

    // not hidden
    var notHiddenClass = lookup.defineClass(fooBytes);
    var classObject = notHiddenClass.getConstructor().newInstance();
    Method classMethod =
        classObject.getClass().getDeclaredMethod("convertToUpperCase", String.class);
    var out2 = classMethod.invoke(classObject, "Hello");
    System.out.println(out2);

    // can't get
    var getHidden = Class.forName("com.denniskao.HiddenClass$Foo/0x0000000800c01000");
    // can get
    var getNotHidden = Class.forName("com.denniskao.HiddenClass$Foo");

    System.out.println("done");
  }
}
