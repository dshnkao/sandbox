package com.denniskao.netty.proxy;

import io.netty.channel.*;

import io.netty.channel.SimpleChannelInboundHandler;

public class HttpProxyBackendHandler extends SimpleChannelInboundHandler<String> {
  private final Channel inboundChannel;

  public HttpProxyBackendHandler(Channel inboundChannel) {
    this.inboundChannel = inboundChannel;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    ctx.read();
  }

  @Override
  public void channelRead0(final ChannelHandlerContext ctx, String msg) {
    inboundChannel
        .writeAndFlush(msg)
        .addListener(
            new ChannelFutureListener() {
              @Override
              public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                  ctx.channel().read();
                } else {
                  future.channel().close();
                }
              }
            });
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    HttpProxyFrontendHandler.closeOnFlush(inboundChannel);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    HttpProxyFrontendHandler.closeOnFlush(ctx.channel());
  }
}
