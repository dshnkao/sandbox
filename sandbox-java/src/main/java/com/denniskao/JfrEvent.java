package com.denniskao;

import jdk.jfr.*;

import java.util.concurrent.ThreadLocalRandom;

public class JfrEvent {

    @Name("CustomEvent")
    @Description("An example event")
    @Category("CustomEvent")
    @Enabled()
    static class CustomEvent extends Event {
        @Label("name")
        public String name;
        @Label("time")
        public int timeMs;
    }

    public static void main(String[] args) throws InterruptedException {
        for (var i = 0;;i++) {
            var event = new CustomEvent();
            event.begin();

            var jitter = ThreadLocalRandom.current().nextInt(500);
            Thread.sleep(jitter);
            event.end();

            event.name = "loop:" + i;
            event.commit();

            Thread.sleep(100);
        }
    }
}
