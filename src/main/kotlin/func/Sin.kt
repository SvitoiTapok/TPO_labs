package com.example.func

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow

class Sin(functionName: String = "Sin"):MatFunction(functionName) {
    override fun invoke(x0: Double, acc: Double): Double {
        if (x0.isNaN() || x0.isInfinite() || acc.isNaN() || acc.isInfinite() || acc <= 0.0) return Double.NaN


        var x = x0 % (2 * PI)
        if(x > PI) x -= 2 * PI
        if(x < -PI) x += 2 * PI

        var res = 0.0
        var i = 0

        while (true) {
            val diff = (-1.0).pow(i) * x.pow(2.0 * i + 1) / factorial(2*i+1, 1)
            res += diff
            if (abs(diff) < acc)
                return res
            i += 1
        }

    }
    fun factorial(x: Int, res:Long):Long{
        if(x==0) return res
        return factorial(x-1, res*x)
    }

}
