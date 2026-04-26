package com.example.func

import com.example.util.CSVGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.mockito.BDDMockito.willReturn
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class Log3UnitTest {
    @Mock
    lateinit var lnMock: Ln

    companion object {
        @JvmStatic
        @BeforeAll
        fun generateCsvData() {
            CSVGenerator(Log3(Ln()), 1.0, 9.0, 5, DEFAULT_ACCURACY).csv()
        }
    }

    @ParameterizedTest(name = "x = {0}, expected log3(x) = {1}")
    @CsvFileSource(files = ["src/test/resources/Log3_data.csv"], numLinesToSkip = 1)
    fun shouldCalculateLog3UsingCsvData(
        xStr: String,
        expectedLogStr: String
    ) {
        val log3 = Log3(lnMock)
        val x = xStr.toDouble()
        val expectedLogValue = expectedLogStr.toDouble()
        val lnX = expectedLogValue
        val lnBase = 1.0
        val accuracy = DEFAULT_ACCURACY

        willReturn(lnX).given(lnMock).invoke(x, accuracy)
        willReturn(lnBase).given(lnMock).invoke(3.0, accuracy)

        val actualResult = log3.invoke(x, accuracy)

        assertEquals(expectedLogValue, actualResult, DEFAULT_ACCURACY)
    }
}
