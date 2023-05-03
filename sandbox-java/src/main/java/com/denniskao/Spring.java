package com.denniskao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Spring {
  public static void main(String[] args) {
      var ctx = new AnnotationConfigApplicationContext();
      ctx.refresh();
  }
}
