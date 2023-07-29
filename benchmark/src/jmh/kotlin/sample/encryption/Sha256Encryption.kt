package sample.encryption

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


@State(Scope.Benchmark)
open class Sha256Encryption {
    //SHA256,
    private val threadLocalDigest = ThreadLocal.withInitial { MessageDigest.getInstance("SHA-256") }

    /**
     * hash 값을 byteArray 형태로 반환한다. input 역시 byteArray 로 주어져야 함.
     */
    @Benchmark
    fun benchmarkSha256(): String {
        val messageDigest = threadLocalDigest.get()
        val hashValue = messageDigest.digest("myinput".toByteArray(StandardCharsets.UTF_8))
        return hashValue.joinToString("") { "%02x".format(it) }
    }
}