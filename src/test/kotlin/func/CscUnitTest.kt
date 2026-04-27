package com.example.func

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.BDDMockito.willReturn
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CscUnitTest {
    @Mock
    lateinit var sinMock: Sin

    @ParameterizedTest(name = "x = {0}, expected csc(x) = {1}")
    @CsvFileSource(files = ["src/test/resources/Csc_data.csv"], numLinesToSkip = 1)
    fun shouldCalculateCscUsingCsvData(
        xStr: String,
        expectedCscStr: String
    ) {
        val csc = Csc(sinMock)
        val x = xStr.toDouble()
        val expectedCscValue = expectedCscStr.toDouble()
        val sinValue = CsvTestData.value("Sin", x)
        val accuracy = DEFAULT_ACCURACY

        willReturn(sinValue).given(sinMock).invoke(x, accuracy)

        val actualResult = csc.invoke(x, accuracy)

        assertEquals(expectedCscValue, actualResult, DEFAULT_ACCURACY)
    }

    @ParameterizedTest(name = "x = {0}")
    @ValueSource(doubles = [0.0, 3.141592653589793])
    fun shouldThrowWhenSinIsZero(
        x: Double
    ) {
        val csc = Csc(sinMock)
        val accuracy = DEFAULT_ACCURACY

        willReturn(0.0).given(sinMock).invoke(x, accuracy)

        assertThrows(ArithmeticException::class.java) {
            csc.invoke(x, accuracy)
        }
    }
}
