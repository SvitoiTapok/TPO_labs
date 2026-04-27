package com.example.func

import kotlin.math.PI
import kotlin.math.pow

class Cos(
    private val sin: Sin,
    functionName: String = "Cos"
): MatFunction(functionName) {
    override fun invoke(x0: Double, acc: Double): Double {
        var x = x0 % (2 * PI)
        if(x > PI) x -= 2 * kotlin.math.PI
        if(x < -PI) x += 2 * kotlin.math.PI
        if(x>PI/2||x<-PI/2) return -(1-sin.invoke(x, acc).pow(2.0)).pow(0.5)
        return (1-sin.invoke(x, acc).pow(2.0)).pow(0.5)
    }
}
