package com.denniskao.classloading;

import com.sun.tools.attach.VirtualMachineDescriptor;

public class DisplayClassLoaders {
  public static void main(String[] args) {
    //
    var clThis = DisplayClassLoaders.class.getClassLoader();
    System.out.println(clThis);
    // prints null, security feature
    // bootstrap classloader does no verification and provides full security
    // access to every class it loads.
    var clObj = Object.class.getClassLoader();
    System.out.println(clObj);
    //
    var clAttach = VirtualMachineDescriptor.class.getClassLoader();
    System.out.println(clAttach);
  }
}
