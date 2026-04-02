package task1

import com.example.task1.maclarenTan
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import kotlin.test.Test
import kotlin.test.assertEquals

class TanTest {
    private val delta = 1e-9
    @ParameterizedTest
    @CsvFileSource(
        resources = ["/task1/tanFamous.csv"],
        numLinesToSkip = 1
    )
    fun famousValuesTest(x:Double, expected: Double) {
        assertEquals(expected, maclarenTan(x), delta)
    }
    @Test
    fun infinityTest() {
        assertEquals(Double.NaN, maclarenTan(Double.POSITIVE_INFINITY))
        assertEquals(Double.NaN, maclarenTan(Double.NEGATIVE_INFINITY))
    }
    @Test
    fun nanTest(){
        assertEquals(Double.NaN, maclarenTan(Double.NaN), delta)
    }
    @ParameterizedTest
    @CsvFileSource(
        resources = ["/task1/tanBig.csv"],
        numLinesToSkip = 1
    )
    fun bigValuesTest(x:Double, expected:Double) {
        assertEquals(expected, maclarenTan(x), delta)
    }

}