package org.example.kotlin.client

import com.example.tcpecho.server.EchoServer
import org.apache.logging.log4j.LogManager
import java.net.StandardProtocolFamily
import java.net.UnixDomainSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.nio.charset.StandardCharsets
import java.nio.file.Paths


fun main() {

    val logger = LogManager.getLogger(EchoServer::class.java)

    val socketPath = Paths.get("/Users/JEKIM/lab")
    val clientSocketChannel = SocketChannel.open(StandardProtocolFamily.UNIX)
    clientSocketChannel.connect(UnixDomainSocketAddress.of(socketPath))

    val messageToSend = "Hello, server!"
    val buffer = ByteBuffer.allocate(1024)
    buffer.clear()
    buffer.put(messageToSend.toByteArray(StandardCharsets.UTF_8))
    buffer.flip()
    clientSocketChannel.write(buffer)
    buffer.clear()
    val bytesRead = clientSocketChannel.read(buffer)
    buffer.flip()
    if (bytesRead > 0) {
        val receivedMessage = StandardCharsets.UTF_8.decode(buffer).toString()
        logger.info("Received message from server: $receivedMessage")
    }

    clientSocketChannel.close()
}

