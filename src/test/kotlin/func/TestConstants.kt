package com.example.func

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.nio.file.Paths
import kotlin.math.abs

const val DEFAULT_ACCURACY = 1e-2

object CsvTestData {
    private const val LOOKUP_EPS = 1e-4
    private val cache = mutableMapOf<String, Map<Double, Double>>()

    fun value(functionName: String, x: String): Double {
        return value(functionName, x.toDouble())
    }

    fun value(functionName: String, x: Double): Double {
        val data = dataByX("${functionName}_data.csv")
        data[x]?.let { return it }

        return data.entries
            .firstOrNull { (argument, _) -> abs(argument - x) < LOOKUP_EPS }
            ?.value
            ?: error("No CSV value for $functionName($x)")
    }

    private fun dataByX(fileName: String): Map<Double, Double> {
        return cache.getOrPut(fileName) {
            Paths.get("src/test/resources", fileName).toFile()
                .readLines()
                .drop(1)
                .associate { line ->
                    val (x, value) = line.split(",", limit = 2)
                    x.toDouble() to value.toDouble()
                }
        }
    }
}

fun assertCsvDoubleEquals(expected: Double, actual: Double, tolerance: Double = DEFAULT_ACCURACY) {
    if (expected.isNaN()) {
        assertTrue(actual.isNaN(), "Expected NaN, but was $actual")
    } else {
        assertEquals(expected, actual, tolerance)
    }
}
