package com.example.task1

import task1.bernully
import task1.factorial
import kotlin.math.abs
import kotlin.math.pow

fun maclarenTan(x1: Double): Double {
    val accuracy = 1e-9
    println("x1"+x1)
    if (x1.isNaN()) return Double.NaN
    if (x1.isInfinite()) return Double.NaN

    val x0 = x1 % Math.PI
    var x = x0
    if(x>Math.PI/2) x -= Math.PI
    if(x<-Math.PI/2) x += Math.PI

    if (abs(x - Math.PI / 2) < 0.0000001) return Double.POSITIVE_INFINITY
    if (abs(x + Math.PI / 2) < 0.0000001) return Double.NEGATIVE_INFINITY
    println(x)
    if (abs(x) > 0.2) {
        val res = maclarenTan(x / 2)
        return 2 * res / (1 - res.pow(2.0))
    }
    var res = 0.0
    var i = 1

    while (true) {
        val diff = abs(bernully(2 * i)) * 2.0.pow(2 * i) * (2.0.pow(2 * i) - 1) * x.pow(2 * i - 1) / factorial(2 * i)
        res += diff
        if (abs(diff) < accuracy)
            return res
        i += 1
    }

}


fun main() {
    println(maclarenTan(0.0 + Math.PI))
}