package com.huiboz.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatterAndGather {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        System.out.println("waiting to be accept");
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("accepted");
        int messageLength = 2;
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long length = socketChannel.read(byteBuffers);
                byteRead += length;
                System.out.println("length=" + length);
                System.out.println("byteRead=" + byteRead);
            }

            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long length = socketChannel.write(byteBuffers);
                byteWrite += length;
            }

            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.clear();
            });

            System.out.println("byteRead=" + byteRead + " byteWrite=" + byteWrite + " messageLength=" + messageLength);
        }

    }
}
