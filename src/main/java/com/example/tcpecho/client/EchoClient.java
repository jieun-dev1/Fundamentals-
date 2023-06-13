package com.example.tcpecho.client;

import com.example.tcpecho.configuration.PortConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class EchoClient {
    private static final Logger logger = LogManager.getLogger(EchoClient.class);
    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static EchoClient instance;
    private PortConfig portConfig;

    private EchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", portConfig.findPort()));
            buffer = ByteBuffer.allocate(256);
        } catch (IOException e) {
            logger.error("An error occurred", e);
        }
    }

    public static void main(String[] args) {
        start();
        sendMessage("hello");
        sendMessage("world");
    }

    public static EchoClient start() {
        if (instance == null)
            instance = new EchoClient();
        return instance;
    }

    public static void stop() throws IOException {
        client.close();
        buffer = null;
    }

    public static String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        String response = null;
        try {
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            response = new String(buffer.array()).trim();
            logger.info("response=" + response);
            buffer.clear();

        } catch (IOException e) {
            logger.error("An error occurred", e);
        }
        return response;
    }
}
