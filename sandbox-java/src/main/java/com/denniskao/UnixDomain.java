package com.denniskao;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class UnixDomain {
    public static void main(String[] args) throws IOException {
        var server = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
        var address = UnixDomainSocketAddress.of("./abc.socket");
        server.bind(address);


        while (true) {
            var channel = server.accept();
            var buf = ByteBuffer.allocate(100);
            channel.read(buf);
            System.out.println(StandardCharsets.UTF_8.decode(buf.flip()));
        }
    }
}
