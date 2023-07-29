package sample.lock

import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.util.concurrent.atomic.AtomicInteger

@State(Scope.Benchmark)
open class AtomicInteger {

    lateinit var atomicInteger: AtomicInteger

    @Setup
    fun setup() {
        atomicInteger = AtomicInteger()
    }

    @Benchmark
    fun benchmarkAtomicInteger(): Int {
        val cntCoroutines = 100_000
        runBlocking {
            val jobs = List(cntCoroutines) {
                launch {
                    atomicInteger.incrementAndGet()
                }
            }
            joinAll(*jobs.toTypedArray())
        }
        return atomicInteger.get()
    }
}