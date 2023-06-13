package org.example.kotlin.server

import com.example.tcpecho.server.EchoServer
import org.apache.logging.log4j.LogManager
import java.net.StandardProtocolFamily
import java.net.UnixDomainSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

fun main() {
    val logger = LogManager.getLogger(EchoServer::class.java)

    //creates a socket file
    val socketPath = Paths.get("/Users/JEKIM/lab")
    val socketAddress = UnixDomainSocketAddress.of(socketPath)

    //without this block, it returns an address already exists exception
    if (Files.exists(socketPath)) {
        Files.delete(socketPath)
    }

    val serverSocketChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX)
    serverSocketChannel.bind(socketAddress)
    val channel = serverSocketChannel.accept()
    val isRunning = true

    try {
        while (isRunning) {
            val message = readSocketMessage(channel)
            if (message.isPresent) {
                val content = message.get()
                logger.info("[Client message] $content")
                val response = "nice to meet you, client"
                channel.write(ByteBuffer.wrap(response.toByteArray()))
            } else {
                logger.info("message ends")
                break
            }
            Thread.sleep(1000)
        }
    } catch (e: Exception) {
        logger.error("error occured while communicating with clients")
    } finally {
        channel.close()
        serverSocketChannel.close()
    }

}

fun readSocketMessage(channel: SocketChannel): Optional<String> {
    val buffer = ByteBuffer.allocate(1024)
    val bytesRead = channel.read(buffer)
    if (bytesRead < 0)
        return Optional.empty();
    val bytes = ByteArray(bytesRead)
    buffer.flip()
    buffer.get(bytes)
    val message = String(bytes)
    return Optional.of(message)
}

