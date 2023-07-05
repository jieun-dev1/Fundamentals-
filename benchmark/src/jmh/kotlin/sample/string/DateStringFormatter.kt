package sample.string

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@State(Scope.Benchmark)
open class DateStringFormatter {
    val calendar = Calendar.getInstance().time
    val localDateTime: LocalDateTime = LocalDateTime.now()

    @Benchmark
    fun convertCalendarToString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(calendar)
    }

    @Benchmark
    fun convertLocalDateTimeToString(): String {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}
