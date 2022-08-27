package com.denniskao;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * An HTTP server that sends back the content of the received HTTP request
 * in a pretty plaintext form.
 */
public final class NettyHelloWorldServer {

    static class HttpHelloWorldServerHandler extends SimpleChannelInboundHandler<HttpObject> {
        private static final byte[] CONTENT = "Hello World".getBytes(StandardCharsets.UTF_8);

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
            if (msg instanceof HttpRequest req) {

                var keepAlive = HttpUtil.isKeepAlive(req);
                var response = new DefaultFullHttpResponse(req.protocolVersion(), OK,
                        Unpooled.wrappedBuffer(CONTENT));
                response.headers()
                        .set(CONTENT_TYPE, TEXT_PLAIN)
                        .setInt(CONTENT_LENGTH, response.content().readableBytes());

                if (keepAlive) {
                    if (!req.protocolVersion().isKeepAliveDefault()) {
                        response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                    }
                } else {
                    // Tell the client we're going to close the connection.
                    response.headers().set(CONNECTION, CLOSE);
                }
                var f = ctx.write(response);
                System.out.println("writing: " + response);
                if (!keepAlive) {
                    f.addListener(ChannelFutureListener.CLOSE);
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    static class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {

        private final SslContext sslCtx;

        public HttpHelloWorldServerInitializer(SslContext sslCtx) {
            this.sslCtx = sslCtx;
        }

        @Override
        public void initChannel(SocketChannel ch) {
            ChannelPipeline p = ch.pipeline();
            if (sslCtx != null) {
                p.addLast(sslCtx.newHandler(ch.alloc()));
            }
            p.addLast(new HttpServerCodec());
            p.addLast(new HttpServerExpectContinueHandler());
            p.addLast(new HttpHelloWorldServerHandler());
        }
    }

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8080"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            var ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        var bossGroup = new NioEventLoopGroup(1);
        var workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpHelloWorldServerInitializer(sslCtx));

            var ch = b.bind(PORT).sync().channel();

            System.err.println("Open your web browser and navigate to " +
                    (SSL ? "https" : "http") + "://127.0.0.1:" + PORT + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
