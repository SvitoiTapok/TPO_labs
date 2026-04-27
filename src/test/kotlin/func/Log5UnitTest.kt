package com.example.func

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.mockito.BDDMockito.willReturn
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class Log5UnitTest {
    @Mock
    lateinit var lnMock: Ln

    @ParameterizedTest(name = "x = {0}, expected log5(x) = {1}")
    @CsvFileSource(files = ["src/test/resources/Log5_data.csv"], numLinesToSkip = 1)
    fun shouldCalculateLog5UsingCsvData(
        xStr: String,
        expectedLogStr: String
    ) {
        val log5 = Log5(lnMock)
        val x = xStr.toDouble()
        val expectedLogValue = expectedLogStr.toDouble()
        val lnX = CsvTestData.value("Ln", xStr)
        val lnBase = CsvTestData.value("Ln", 5.0)
        val accuracy = DEFAULT_ACCURACY

        willReturn(lnX).given(lnMock).invoke(x, accuracy)
        willReturn(lnBase).given(lnMock).invoke(5.0, accuracy)

        val actualResult = log5.invoke(x, accuracy)

        assertEquals(expectedLogValue, actualResult, DEFAULT_ACCURACY)
    }
}
