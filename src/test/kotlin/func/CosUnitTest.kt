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
import kotlin.math.PI

@ExtendWith(MockitoExtension::class)
class CosUnitTest {
    @Mock
    lateinit var sinMock: Sin

    companion object {
        @JvmStatic
        @BeforeAll
        fun generateCsvData() {
            CSVGenerator(Cos(Sin()), 0.0, 1.0, 5, DEFAULT_ACCURACY).csv()
        }
    }

    @ParameterizedTest(name = "x = {0}, expected cos(x) = {1}")
    @CsvFileSource(files = ["src/test/resources/Cos_data.csv"], numLinesToSkip = 1)
    fun shouldCalculateCosUsingCsvData(
        xStr: String,
        expectedCosStr: String
    ) {
        val cos = Cos(sinMock)
        val x = xStr.toDouble()
        val expectedCosValue = expectedCosStr.toDouble()
        val accuracy = DEFAULT_ACCURACY
        val expectedSinArgument = PI / 2 - x

        willReturn(expectedCosValue).given(sinMock).invoke(expectedSinArgument, accuracy)

        val actualResult = cos.invoke(x, accuracy)

        assertEquals(expectedCosValue, actualResult, DEFAULT_ACCURACY)
    }
}
