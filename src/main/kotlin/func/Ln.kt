package com.example.func

import kotlin.math.E
import kotlin.math.abs

class Ln(functionName: String = "Ln") : MatFunction(functionName) {
    override fun invoke(x0: Double, acc: Double): Double {
        if (x0.isNaN() || x0.isInfinite() || x0 <= 0.0 || acc.isNaN() || acc.isInfinite() || acc <= 0.0) {
            return Double.NaN
        }

        var x = x0
        var exponentShift = 0

        while (x > E) {
            x /= E
            exponentShift += 1
        }

        while (x < 1 / E) {
            x *= E
            exponentShift -= 1
        }

        val z = (x - 1) / (x + 1)
        val zSquared = z * z
        var zPower = z
        var denominator = 1.0
        var result = 0.0

        while (true) {
            val diff = zPower / denominator
            result += diff

            if (abs(2 * diff) < acc) {
                return 2 * result + exponentShift
            }

            zPower *= zSquared
            denominator += 2.0
        }
    }
}
