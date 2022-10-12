package com.denniskao.timeouts;

public class InterruptSync {
    public static void main(String[] args) {
        var lock = new Object();
        var locked = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                }
            }
        });
        var job = new Thread(() -> {
            synchronized (lock) {
                System.out.println("acquire");
            }
        });
        locked.start();
        job.start();
        job.interrupt();
        locked.interrupt();
    }
}
