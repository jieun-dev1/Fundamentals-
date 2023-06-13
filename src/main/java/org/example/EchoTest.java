package org.example;

import org.junit.*;


import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EchoTest {
    Process server;
    EchoClient client;

    @Before
    public void setup() throws IOException, InterruptedException {
        server = EchoServer.start();
        client = EchoClient.start();
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect(){
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }

    @After
    public void close() throws IOException {
        server.destroy();
        client.stop();
    }
}
