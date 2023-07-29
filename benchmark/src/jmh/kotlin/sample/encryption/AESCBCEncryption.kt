package sample.encryption

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

@State(Scope.Benchmark)
open class AESCBCEncryption {
    lateinit var cipher: Cipher

    @Setup
    fun setUp() {
        val key = generateKey()
        val iv = generateIV()
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
    }

    @Benchmark
    fun benchmarkAesCBCEncryption(): ByteArray {
        return cipher.doFinal("myinput".toByteArray(StandardCharsets.UTF_8))
    }

    fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }


    /**
     * Iv must be 16th - CBC
     */
    fun generateIV(): ByteArray {
        //iv is immutable, but the Byte Array object can be modified.
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return iv
    }
}