package com.denniskao.timeouts;

import java.util.concurrent.Executors;

public class FutureTaskSleep {
    public static void main(String[] args) {
        var executor = Executors.newSingleThreadExecutor();
        var job = executor.submit(() -> {});
    }
}
