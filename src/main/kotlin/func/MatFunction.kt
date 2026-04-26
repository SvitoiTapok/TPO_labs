package com.example.func

import java.math.BigDecimal

open class MatFunction(val functionName: String) {
    open fun invoke(x: Double, acc: Double): Double {
        return x;
    }
    fun invoke(x: Double): Double {
        return invoke(x, 1e-6)
    }
}