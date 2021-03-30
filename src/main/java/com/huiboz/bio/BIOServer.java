package com.huiboz.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
A blocking IO server
telnet localhost 7777
 */
public class BIOServer {
    final static int PORT_NUM = 7777;


    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT_NUM);
        } catch (final Exception ex) {
            System.out.println("Failed to initialize serverSocket: " + ex);
            return;
        }

        System.out.println("server start!");

        while (true) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (final Exception ex) {
                System.out.println("Failed to build clientSocket: " + ex);
                return;
            }

            System.out.println("one connection build!");

            executorService.execute(new Thread() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        final byte[] bytes = new byte[1024];
                        final InputStream inputStream = clientSocket.getInputStream();
                        while (true) {
                            System.out.println("check: " + Thread.currentThread().getName());
                            final int read = inputStream.read(bytes);
                            if (read != -1) {
                                System.out.println("read: " + new String(bytes, 0 , read));
                            } else {
                                System.out.println("read is -1");
                                break;
                            }
                        }
                    } catch (final Exception ex) {
                        System.out.println("read bytes fail: " + ex);
                    } finally {
                        System.out.println("close connection!");
                        try {
                            clientSocket.close();
                        } catch (final Exception ex) {
                            System.out.println("fail to close clientsocket: " + ex);
                        }
                    }
                }
            });

        }
    }
}
