package sample.string

import org.openjdk.jmh.annotations.*
import kotlin.random.Random

@State(Scope.Benchmark)
open class StringProcessor {

    val regexPattern = Regex("^abc")
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
    lateinit var inputString: String

    @Setup(Level.Iteration)
    fun setup() {
        inputString = randomStringByKotlinRandom()
    }

    private fun randomStringByKotlinRandom() = (1..20).map {
        Random.nextInt(0, charPool.size).let { charPool[it] }
    }.joinToString("")

    @Benchmark
    fun regexMatching(): Boolean {
        return regexPattern.matches(inputString)
    }

    @Benchmark
    fun stringPrefixMatching(): Boolean {
        return inputString.startsWith("abc")
    }

    @Benchmark
    fun inOperatorMatching(): Boolean {
        return "abc" in "bbcd"
    }

    @Benchmark
    fun regexInOperatorMatching(): Boolean {
        val regex = Regex("^abc")
        return regex.matches("bbcd")
    }
}


