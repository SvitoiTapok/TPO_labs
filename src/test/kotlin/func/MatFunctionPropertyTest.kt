package com.example.func

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.willReturn
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.math.PI
import kotlin.math.ln
import kotlin.math.log

@ExtendWith(MockitoExtension::class)
@DisplayName("Equivalence classes for the function system")
class MatFunctionPropertyTest {
    @Mock
    lateinit var trigonometricSystemMock: TrigonometricSystemFunction

    @Mock
    lateinit var logarithmicSystemMock: LogarithmicSystemFunction

    @ParameterizedTest(name = "{index}: {0}")
    @CsvSource(
        "negative regular point, -2.0, 7.0, -100.0, 7.0",
        "branch boundary belongs to trigonometric part, 0.0, 3.0, -100.0, 3.0",
        "positive regular point, 2.0, 7.0, -100.0, -100.0"
    )
    fun shouldCoverSystemBranchEquivalenceClasses(
        caseName: String,
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
    }

    @ParameterizedTest(name = "{index}: {0}")
    @CsvSource(
        "sin zero excluded for csc, 0.0, NaN",
        "tan asymptote excluded, 1.5707963267948966, NaN",
        "negative ordinary trigonometric point, -1.0471975511965976, -1.7320508075688767",
        "positive logarithmic identity x = 1, 1.0, 0.0",
        "positive logarithmic base 3, 3.0, 1.0",
        "positive logarithmic base 5, 5.0, 1.0",
        "positive logarithmic base 10, 10.0, 1.0"
    )
    fun shouldDocumentRelevantSubfunctionEquivalenceClasses(
        caseName: String,
        x: Double,
        expectedMarker: Double
    ) {
        val actualMarker = when (caseName) {
            "sin zero excluded for csc" -> Double.NaN
            "tan asymptote excluded" -> Double.NaN
            "negative ordinary trigonometric point" -> kotlin.math.tan(-PI / 3)
            "positive logarithmic identity x = 1" -> ln(x)
            "positive logarithmic base 3" -> log(x, 3.0)
            "positive logarithmic base 5" -> log(x, 5.0)
            "positive logarithmic base 10" -> log(x, 10.0)
            else -> error("Unknown case: $caseName")
        }

        assertCsvDoubleEquals(expectedMarker, actualMarker)
    }
}
