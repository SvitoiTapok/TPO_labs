package com.example.func

import kotlin.math.PI
import kotlin.math.pow

class Cos(
    private val sin: Sin,
    functionName: String = "Cos"
): MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        return (1-sin.invoke(x, acc).pow(2.0)).pow(0.5)
    }
}
