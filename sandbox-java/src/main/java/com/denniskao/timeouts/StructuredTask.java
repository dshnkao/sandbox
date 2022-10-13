package com.denniskao.timeouts;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.ExecutionException;

public class StructuredTask {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // scope
        //   --> findFoo
        //   --> findBar
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var foo = scope.fork(StructuredTask::findFoo);
            var bar = scope.fork(StructuredTask::findBar);
            scope.join();           // Join both forks
            scope.throwIfFailed();  // ... and propagate errors
            System.out.println(foo.get() + bar.get());
        }
    }

    static String findFoo() throws InterruptedException {
        System.out.println("foo: " + Thread.currentThread().getThreadGroup().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("foo: interrupted: ");
            throw e;
        }
        System.out.println("foo: done");
        return "foo";
    }

    static String findBar() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("bar: " + Thread.currentThread().getThreadGroup().getName());
        throw new RuntimeException("bar");
    }

}
