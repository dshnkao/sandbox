package com.denniskao.netty.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {

    private static final int LOCAL_PORT = 8080;
    private static final String REMOTE_HOST = "localhost";
    private static final int REMOTE_PORT = 8081;

  public static void main(String[] args) throws InterruptedException {
    System.err.println(
        "Proxying *:" + LOCAL_PORT + " to " + REMOTE_HOST + ':' + REMOTE_PORT + " ...");
    // Configure the bootstrap.
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new HttpProxyInitializer(REMOTE_HOST, REMOTE_PORT))
          .childOption(ChannelOption.AUTO_READ, false)
          .bind(LOCAL_PORT)
          .sync()
          .channel()
          .closeFuture()
          .sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
