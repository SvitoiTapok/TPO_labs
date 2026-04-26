package com.example.func

class Tan(
    private val sin: Sin,
    private val cos: Cos,
    functionName: String = "Tan"
): MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        val cosRes = cos.invoke(x, acc)
        if (cosRes == 0.0) throw ArithmeticException("tan undefined: cos($x)==0")
        return sin.invoke(x, acc) / cosRes
    }
}
