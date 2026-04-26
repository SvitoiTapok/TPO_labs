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
class Log10UnitTest {
    @Mock
    lateinit var lnMock: Ln

    companion object {
        @JvmStatic
        @BeforeAll
        fun generateCsvData() {
            CSVGenerator(Log10(Ln()), 1.0, 100.0, 5, DEFAULT_ACCURACY).csv()
        }
    }

    @ParameterizedTest(name = "x = {0}, expected log10(x) = {1}")
    @CsvFileSource(files = ["src/test/resources/Log10_data.csv"], numLinesToSkip = 1)
    fun shouldCalculateLog10UsingCsvData(
        xStr: String,
        expectedLogStr: String
    ) {
        val log10 = Log10(lnMock)
        val x = xStr.toDouble()
        val expectedLogValue = expectedLogStr.toDouble()
        val lnX = expectedLogValue
        val lnBase = 1.0
        val accuracy = DEFAULT_ACCURACY

        willReturn(lnX).given(lnMock).invoke(x, accuracy)
        willReturn(lnBase).given(lnMock).invoke(10.0, accuracy)

        val actualResult = log10.invoke(x, accuracy)

        assertEquals(expectedLogValue, actualResult, DEFAULT_ACCURACY)
    }
}
