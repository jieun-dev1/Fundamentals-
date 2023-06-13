package org.example;

import com.example.tcpecho.client.EchoClient;
import com.example.tcpecho.server.EchoServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class EchoTest {
    Process server;
    EchoClient client;

    @Before
    public void setup() throws IOException, InterruptedException {
        client = EchoClient.start();
        server = EchoServer.start();
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        Assert.assertEquals("hello", resp1);
        Assert.assertEquals("world", resp2);
    }

    @After
    public void close() throws IOException {
        server.destroy();
        client.stop();
    }
}
