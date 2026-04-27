package com.example.func

class Csc(
    private val sin: Sin,
    functionName: String = "Csc"
):MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        val res = sin.invoke(x, acc)
        if (res == 0.0) throw ArithmeticException("csc undefined: sin($x)==0")
        return 1/res
    }
}