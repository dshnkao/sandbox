package com.denniskao;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;

public class UnixDomain {
    public static void main(String[] args) throws IOException {
        var acceptor = ServerSocketChannel.open(StandardProtocolFamily.UNIX)
                .socket()
                .bind(UnixDomainSocketAddress.of("./abc"));

        while (true) {
            var channel = acceptor.accept();
            var buf = ByteBuffer.allocate(100);
            channel.read(buf);
            System.out.println(StandardCharsets.UTF_8.decode(buf.flip()));
        }
    }
}
