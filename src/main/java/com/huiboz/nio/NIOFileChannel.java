package com.huiboz.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {
    public static void main(String[] args) throws Exception {
//        writeExample();
//        readExample();
//        readAndWriteWithOneBufferExample();
        transferFromExample();
    }

    private static void transferFromExample() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("a.png");
        FileOutputStream fileOutputStream = new FileOutputStream("b.png");

        FileChannel fileInputChannel = fileInputStream.getChannel();
        FileChannel fileOutputChannel = fileOutputStream.getChannel();

        fileOutputChannel.transferFrom(fileInputChannel, 0, fileInputChannel.size());

        fileInputStream.close();
        fileOutputChannel.close();
    }

    private static void readAndWriteWithOneBufferExample() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileInputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileOutputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(7);

        while (true) {

            byteBuffer.clear();

            int read = fileInputChannel.read(byteBuffer);
            System.out.println("read: "  + read);
            if (read == -1) { // read done
                break;
            }
            // write data from buffer to fileOutputChannel
            byteBuffer.flip();
            fileOutputChannel.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }

    private static void readExample() throws Exception {
        File file = new File("/Users/huibozhao/Desktop/sample.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }

    private static void writeExample() throws Exception {
        final String str = "hello world";
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/huibozhao/Desktop/sample.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        // channel.read(Destination) data goes from channel to destination buffer
        // channel.write(src) data goes from src buffer to channel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
