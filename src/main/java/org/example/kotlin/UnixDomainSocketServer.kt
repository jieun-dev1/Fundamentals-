package org.example.kotlin

import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.net.StandardProtocolFamily
import java.net.UnixDomainSocketAddress
import java.nio.channels.ServerSocketChannel
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

    fun main () {
        //creates a socket file
        val socketPath = Paths.get("/Users/JEKIM/lab")
        val socketAddress = UnixDomainSocketAddress.of(socketPath)

        //without this block, it returns an address already exists exception
        if(Files.exists(socketPath)) {
            Files.delete(socketPath)
        }

        val serverSocketChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX)
        serverSocketChannel.bind(socketAddress)
        val channel = serverSocketChannel.accept()
        val isRunning = true

        try {
            while(isRunning) {
                val message = readSocketMessage(channel)
                if(message.isPresent) {
                    val content = message.get()
                    println("[Client message] $content")
                    val response = "nice to meet you, client"
                    channel.write(ByteBuffer.wrap(response.toByteArray()))
                } else {
                    println("message ends")
                    break
                }
                Thread.sleep(1000)
            }
        } catch (e: Exception){
            println("error occured while communicating with clients")
        }

        finally {
            channel.close()
            serverSocketChannel.close()
        }

    }

    fun readSocketMessage(channel: SocketChannel): Optional<String> {
        val buffer = ByteBuffer.allocate(1024)
        val bytesRead = channel.read(buffer)
        if(bytesRead<0)
            return Optional.empty();
        val bytes = ByteArray(bytesRead)
        buffer.flip()
        buffer.get(bytes)
        val message = String(bytes)
        return Optional.of(message)
    }

