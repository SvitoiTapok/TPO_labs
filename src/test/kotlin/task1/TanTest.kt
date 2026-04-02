package task1

import com.example.task1.maclarenTan
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import kotlin.test.assertEquals

class TanTest {
    private val delta = 1e-6
    @ParameterizedTest
    @CsvFileSource(
        resources = ["/task1/tan.csv"],
        numLinesToSkip = 1
    )
    fun tanTest(x:Double, expected: Double) {
        assertEquals(expected, maclarenTan(x), delta)
    }
}