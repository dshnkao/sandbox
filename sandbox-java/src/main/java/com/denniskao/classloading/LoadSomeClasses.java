package com.denniskao.classloading;

import java.util.List;

/**
 * Shows a custom class loader
 */
public class LoadSomeClasses {
  public static class SadClassloader extends ClassLoader {
    public SadClassloader() {
      super(SadClassloader.class.getClassLoader());
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
      // Free to add extra capabilities. e.g.
      // - load over the network.
      // - load encrypted classes.
      System.out.println("Loading Failed: " + name);
      throw new ClassNotFoundException(name);
    }
  }

  public static void main(String[] args) {
    var clazzes = List.of("test", "com.denniskao.classloading.LoadSomeClasses$SadClassloader");
    var loader = new SadClassloader();
    for (var name : clazzes) {
      System.out.println("Loading: " + name);
      try {
        var clazz = loader.loadClass(name);
        System.out.println("Loaded: " + clazz);
      } catch (ClassNotFoundException ex) {
        ex.printStackTrace();
      }
    }
  }
}
