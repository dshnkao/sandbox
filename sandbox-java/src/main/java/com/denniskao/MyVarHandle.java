package com.denniskao;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 *
 * https://openjdk.org/jeps/193
 * AtomicReference without the Object overhead. Replaces Unsafe.
 */
public class MyVarHandle {

    int i;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        var obj = new MyVarHandle();
        var handle = MethodHandles.lookup().in(MyVarHandle.class)
                .findVarHandle(MyVarHandle.class, "i", int.class);
        handle.accessModeType(VarHandle.AccessMode.GET);
        handle.set(obj, 2);
        System.out.println(handle.getVolatile(obj));
        System.out.println(handle.varType());
    }
}
