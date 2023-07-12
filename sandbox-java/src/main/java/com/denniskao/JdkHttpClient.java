package com.denniskao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.Executors;

public class JdkHttpClient {

  public static void main(String[] args) throws IOException, InterruptedException {
    var client = HttpClient.newBuilder()
        .executor(Executors.newSingleThreadExecutor())
        .build();

    var req = HttpRequest.newBuilder().GET().uri(URI.create("https://www.canva.com/_online"))
        .build();

    // not blocks, but future off-loads to ASYNC_POOL
    var resAsync = client.sendAsync(req, BodyHandlers.ofString());
    resAsync.whenComplete((resp, ex) -> {
      System.out.println(Thread.currentThread().getName());
    });
    System.out.println(resAsync.join());

    // blocks
    // doesn't off-load to pool
    var res = client.send(req, BodyHandlers.ofString());
    System.out.println(res);

  }
}
