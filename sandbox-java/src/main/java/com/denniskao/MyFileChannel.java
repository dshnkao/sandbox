package com.denniskao;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MyFileChannel {
    public static void main(String[] args) throws IOException {
        var channel = FileChannel.open(Path.of("./large-file"), StandardOpenOption.READ);
        var toChannel = FileChannel.open(Path.of("./another-large-file"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
//        var buffer = ByteBuffer.allocate(2_000_000_000);
        channel.transferTo(0, 2_000, toChannel);
        System.out.println("done");
    }
}
