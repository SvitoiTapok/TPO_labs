package com.example.func

import kotlin.math.PI

class Cos(
    private val sin: Sin,
    functionName: String = "Cos"
): MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        return sin.invoke(PI / 2 - x, acc)
    }
}
