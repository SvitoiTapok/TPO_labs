package com.example.func

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.willReturn
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("Integration strategy: top-down with one module connected per step")
class MatFunctionIntegrationTest {
    @Mock
    lateinit var sinMock: Sin

    @Mock
    lateinit var cscMock: Csc

    @Mock
    lateinit var tanMock: Tan

    @Mock
    lateinit var lnMock: Ln

    @Mock
    lateinit var log3Mock: Log3

    @Mock
    lateinit var log5Mock: Log5

    @Mock
    lateinit var log10Mock: Log10

    @Mock
    lateinit var trigonometricSystemMock: TrigonometricSystemFunction

    @Mock
    lateinit var logarithmicSystemMock: LogarithmicSystemFunction

    @ParameterizedTest(name = "x = {0}, system chooses correct top-level stub")
    @CsvSource(
        "-2.0, 11.5, -100.0, 11.5",
        "0.0, -3.25, 42.0, -3.25",
        "2.0, 11.5, -100.0, -100.0"
    )
    fun shouldRouteSystemFunctionToTrigonometricOrLogarithmicModule(
        x: Double,
        trigonometricValue: Double,
        logarithmicValue: Double,
        expectedValue: Double
    ) {
        val system = SystemFunction(trigonometricSystemMock, logarithmicSystemMock)

        if (x <= 0.0) {
            willReturn(trigonometricValue).given(trigonometricSystemMock).invoke(x, DEFAULT_ACCURACY)
        } else {
            willReturn(logarithmicValue).given(logarithmicSystemMock).invoke(x, DEFAULT_ACCURACY)
        }

        val actualResult = system.invoke(x, DEFAULT_ACCURACY)

        assertCsvDoubleEquals(expectedValue, actualResult)
        if (x <= 0.0) {
            verify(trigonometricSystemMock).invoke(x, DEFAULT_ACCURACY)
            verify(logarithmicSystemMock, never()).invoke(x, DEFAULT_ACCURACY)
        } else {
            verify(logarithmicSystemMock).invoke(x, DEFAULT_ACCURACY)
            verify(trigonometricSystemMock, never()).invoke(x, DEFAULT_ACCURACY)
        }
    }

    @ParameterizedTest(name = "trigonometric module with leaf stubs, x = {0}")
    @CsvFileSource(files = ["src/test/resources/TrigonometricSystemFunction_data.csv"], numLinesToSkip = 1)
    fun shouldIntegrateTrigonometricSystemWithStubbedLeaves(
        xStr: String,
        expectedStr: String
    ) {
        val x = xStr.toDouble()
        val expectedValue = expectedStr.toDouble()
        val trigonometricSystem = TrigonometricSystemFunction(sinMock, cscMock, tanMock)

        willReturn(CsvTestData.value("Sin", x)).given(sinMock).invoke(x, DEFAULT_ACCURACY)
        willReturn(CsvTestData.value("Csc", x)).given(cscMock).invoke(x, DEFAULT_ACCURACY)
        willReturn(CsvTestData.value("Tan", x)).given(tanMock).invoke(x, DEFAULT_ACCURACY)

        val actualResult = trigonometricSystem.invoke(x, DEFAULT_ACCURACY)

        assertCsvDoubleEquals(expectedValue, actualResult)
    }

    @ParameterizedTest(name = "logarithmic module with leaf stubs, x = {0}")
    @CsvFileSource(files = ["src/test/resources/LogarithmicSystemFunction_data.csv"], numLinesToSkip = 1)
    fun shouldIntegrateLogarithmicSystemWithStubbedLeaves(
        xStr: String,
        expectedStr: String
    ) {
        val x = xStr.toDouble()
        val expectedValue = expectedStr.toDouble()
        val logarithmicSystem = LogarithmicSystemFunction(lnMock, log3Mock, log5Mock, log10Mock)

        willReturn(CsvTestData.value("Ln", x)).given(lnMock).invoke(x, DEFAULT_ACCURACY)
        willReturn(CsvTestData.value("Log3", x)).given(log3Mock).invoke(x, DEFAULT_ACCURACY)
        willReturn(CsvTestData.value("Log5", x)).given(log5Mock).invoke(x, DEFAULT_ACCURACY)
        willReturn(CsvTestData.value("Log10", x)).given(log10Mock).invoke(x, DEFAULT_ACCURACY)

        val actualResult = logarithmicSystem.invoke(x, DEFAULT_ACCURACY)

        assertCsvDoubleEquals(expectedValue, actualResult)
    }

    @ParameterizedTest(name = "full real system, x = {0}")
    @CsvFileSource(files = ["src/test/resources/SystemFunction_data.csv"], numLinesToSkip = 1)
    fun shouldCalculateFullSystemWithAllRealModules(
        xStr: String,
        expectedStr: String
    ) {
        val x = xStr.toDouble()
        val expectedValue = expectedStr.toDouble()
        val sin = Sin()
        val cos = Cos(sin)
        val tan = Tan(sin, cos)
        val csc = Csc(sin)
        val ln = Ln()
        val system = SystemFunction(
            TrigonometricSystemFunction(sin, csc, tan),
            LogarithmicSystemFunction(ln, Log3(ln), Log5(ln), Log10(ln))
        )

        val actualResult = system.invoke(x, DEFAULT_ACCURACY)

        assertCsvDoubleEquals(expectedValue, actualResult)
    }

    @Test
    fun shouldDocumentSelectedIntegrationStrategy() {
        val strategy = listOf(
            "1. SystemFunction is tested against top-level stubs to verify the x <= 0 branch split.",
            "2. TrigonometricSystemFunction is connected while Sin, Csc and Tan remain stubs.",
            "3. LogarithmicSystemFunction is connected while Ln, Log3, Log5 and Log10 remain stubs.",
            "4. Full SystemFunction is tested with real modules against the reference CSV table."
        )

        assertCsvDoubleEquals(4.0, strategy.size.toDouble())
    }
}
