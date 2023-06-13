package org.example;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        //open(): create a Selector object / channel
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //bind port, set selectable channel to non-blocking mode, register channel to the selector
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("server is ready");

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    register(selector, serverSocketChannel);
                }

                if (key.isReadable()) {
                    answerWithEcho(buffer, key);
                }
                iter.remove();
            }

        }
    }

    public static void register(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key) throws IOException {
        //SocketChannel extends SelectableChannel
        SocketChannel client = (SocketChannel) key.channel();
        int point = client.read(buffer);
        //end of the input stream. no more bytes available to be read || may indicate error
        if (point == -1) {
            System.out.println("Not accepting client messages anymore");
            client.close();
        } else {
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }
    }

    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classPath = System.getProperty("java.class.path");
        String className = EchoServer.class.getCanonicalName();
        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classPath, className);
        return builder.start();
    }
}