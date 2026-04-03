package task1

import com.example.task1.maclarenTan
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

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
    @Test
    fun pid2Test(){
        assertEquals(Double.NEGATIVE_INFINITY, maclarenTan( Math.PI/2), delta)
        assertEquals(Double.NEGATIVE_INFINITY,  maclarenTan(Math.PI/2+0.00000001), delta)
        assertEquals(Double.POSITIVE_INFINITY,  maclarenTan(Math.PI/2-0.00000001), delta)
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