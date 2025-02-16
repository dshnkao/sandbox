package com.denniskao.blockhound;

import com.denniskao.netty.NettyHelloWorldServer.HttpHelloWorldServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import reactor.blockhound.BlockHound;

public class BhNetty {

  // Netty is automatically integrated.
  // curl localhost:8080 to see exception thrown
  public static void main(String[] args) throws InterruptedException {

    BlockHound
        .builder()
        .loadIntegrations()
        .install();

    var bossGroup = new NioEventLoopGroup(1);
    var workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.option(ChannelOption.SO_BACKLOG, 1024);
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new HttpHelloWorldServerInitializer(null));

      var ch = b.bind(8080).sync().channel();
      ch.closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }


}
