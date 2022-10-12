package com.denniskao.timeouts;

public class InterruptSleep {
    public static void main(String[] args) {
        var job = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        job.start();
        job.interrupt();
    }
}
