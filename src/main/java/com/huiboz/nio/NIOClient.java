package com.huiboz.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("non block here :) ");
            }
        }

        final String str = "hello from client";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // send data, write buffer data into channel
        socketChannel.write(buffer);

        System.in.read();
    }
}
