package com.denniskao;

import java.net.http.HttpClient;

public class HttpClientPool {
  public static void main(String[] args) {
      var client = HttpClient
              .newBuilder()
              .build();

      client.sendAsync(null, null);
  }
}
