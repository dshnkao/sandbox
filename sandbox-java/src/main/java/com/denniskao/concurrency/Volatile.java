package com.denniskao.concurrency;

public class Volatile {
    // add volatile to see change detected by Reader
    private static int sharedInt = 0;

    public static void main(String[] args) {
        new Reader().start();
        new Writer().start();
    }

    static class Reader extends Thread {
        @Override
        public void run() {
            int local = sharedInt;
            while (local < 10000) {
                // println has a synchronized block
                // uncomment to observe change without volatile
                // System.out.println(sharedInt);
                if (local != sharedInt) {
                    System.out.format("Reader: Detected sharedInt change to %d%n", sharedInt);
                    local = sharedInt;
                }
            }
        }
    }
    static class Writer extends Thread {
        @Override
        public void run() {
            while (sharedInt < 10){
                System.out.format("Writer: Increment sharedInt to %d%n", sharedInt + 1);
                sharedInt++;
            }
        }
    }
}