package sample.lock

import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class MutexLock {
    var regularInt: Int = 0
    lateinit var mutex: Mutex

    @Setup
    fun setup() {
        regularInt = 0
        mutex = Mutex()
    }

    @Benchmark
    fun benchmarkMutex(): Int {
        val cntCoroutines = 100_000
        runBlocking {
            val jobs = List(cntCoroutines) {
                launch {
                    mutex.withLock {
                        regularInt++
                    }
                }
            }
            joinAll(*jobs.toTypedArray())
        }
        return regularInt
    }
}