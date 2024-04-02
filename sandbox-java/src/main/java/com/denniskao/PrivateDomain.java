package com.denniskao;

import com.google.common.net.InternetDomainName;

import java.net.URI;
import java.net.URISyntaxException;

public class PrivateDomain {
    public static void main(String[] args) throws URISyntaxException {
        var canva = "https://www.canva.com";
        var localhost = "http://localhost.com:8080";
        var frontend = "http://canva.test:8080";
        var uri = new URI(frontend);
        var host = uri.getHost();
        System.out.println(host);
        var rootDomain = InternetDomainName.from(host).topPrivateDomain();
        System.out.println(rootDomain);
    }
}
