package com.example.func

import com.example.util.CSVGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.BDDMockito.willReturn
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class TanUnitTest {
    @Mock
    lateinit var sinMock: Sin

    @Mock
    lateinit var cosMock: Cos

    companion object {
        @JvmStatic
        @BeforeAll
        fun generateCsvData() {
            val sin = Sin()
            CSVGenerator(Tan(sin, Cos(sin)), 0.0, 1.0, 5, DEFAULT_ACCURACY).csv()
        }
    }

    @ParameterizedTest(name = "x = {0}, expected tan(x) = {1}")
    @CsvFileSource(files = ["src/test/resources/Tan_data.csv"], numLinesToSkip = 1)
    fun shouldCalculateTanUsingCsvData(
        xStr: String,
        expectedTanStr: String
    ) {
        val tan = Tan(sinMock, cosMock)
        val x = xStr.toDouble()
        val expectedTanValue = expectedTanStr.toDouble()
        val sinValue = expectedTanValue
        val cosValue = 1.0
        val accuracy = DEFAULT_ACCURACY

        willReturn(cosValue).given(cosMock).invoke(x, accuracy)
        willReturn(sinValue).given(sinMock).invoke(x, accuracy)

        val actualResult = tan.invoke(x, accuracy)

        assertEquals(expectedTanValue, actualResult, DEFAULT_ACCURACY)
    }

    @ParameterizedTest(name = "x = {0}")
    @ValueSource(doubles = [1.5707963267948966, -1.5707963267948966])
    fun shouldThrowWhenCosIsZero(
        x: Double
    ) {
        val tan = Tan(sinMock, cosMock)
        val accuracy = DEFAULT_ACCURACY

        willReturn(0.0).given(cosMock).invoke(x, accuracy)

        assertThrows(ArithmeticException::class.java) {
            tan.invoke(x, accuracy)
        }
    }
}
