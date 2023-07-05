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
import javax.crypto.spec.GCMParameterSpec

@State(Scope.Benchmark)
open class AESGCMEncryption {

    lateinit var cipher: Cipher
    lateinit var key: SecretKey

    @Setup
    fun setup() {
        key = generateKey()
    }

    @Benchmark
    fun benchmarkAesGcmEncryption(): ByteArray {
        val iv = generateIV()
        cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(128, iv))
        return cipher.doFinal("myinput".toByteArray(StandardCharsets.UTF_8))
    }

    private fun generateIV(): ByteArray {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return iv
    }

    private fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

}