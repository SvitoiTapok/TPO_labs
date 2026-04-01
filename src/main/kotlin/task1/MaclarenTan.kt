package com.example.task1

import task1.bernully
import kotlin.math.abs
import kotlin.math.pow

fun maclarenTan(x1: Double, n: Int = 10): Double {
    if (x1.isNaN()) return Double.NaN
    if (n <= 0) return Double.NaN

    val x = (x1+Math.PI/2)%Math.PI-Math.PI/2
    if(abs( x-Math.PI/2)<0.00000001) return Double.POSITIVE_INFINITY
    if(abs(x+Math.PI/2)<0.00000001) return Double.NEGATIVE_INFINITY

    if (x < -Math.PI/2+0.01 || x>Math.PI/2-0.01) {
        val res = maclarenTan(x/2,n)
        return 2*res/(1+ res.pow(2.0))
    }

    var res = 0.0

    for (i in 1..n) {
        res += abs(bernully(2*i)) * 2.0.pow(2*i)* (2.0.pow(2*i)-1)*x.pow(2*i-1)
    }
    return res
}



fun main(args: Array<String>) {
    println( bernully(1))
}