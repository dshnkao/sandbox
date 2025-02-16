package com.denniskao.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.function.Supplier;

public class DNS {
    static boolean x = false;
    public static void main(String[] args) throws UnknownHostException, InterruptedException {

        Security.setProperty("networkaddress.cache.tl", "30");
        Security.setProperty("networkaddress.cache.negative.ttl", "30");
        while (true) {
            Thread.sleep(1000);
            String ttl = java.security.Security.getProperty("networkaddress.cache.ttl");
            System.out.println(ttl);
        }
    }
}
