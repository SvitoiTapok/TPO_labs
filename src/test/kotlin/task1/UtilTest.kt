package task1

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.api.Assertions.*

class UtilTest {
    private val delta = 1e-9
    @ParameterizedTest
    @CsvFileSource(
        resources = ["/task1/bernully.csv"],
        numLinesToSkip = 1
    )
    fun bernullyTest(n: Int, expected: Double) {
        assertEquals(expected, bernully(n), delta)
    }
    @ParameterizedTest
    @CsvFileSource(
        resources = ["/task1/combC.csv"],
        numLinesToSkip = 1
    )
    fun combCTest(n: Int, m:Int,  expected: Long) {
        assertEquals(expected, combC(n, m))
    }
    @ParameterizedTest
    @CsvFileSource(
        resources = ["/task1/factorial.csv"],
        numLinesToSkip = 1
    )
    fun factorialTest(n: Int, expected: Long) {
        assertEquals(expected, factorial(n))
    }


}