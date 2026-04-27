package com.example.func

class TrigonometricSystemFunction(
    private val sin: Sin,
    private val csc: Csc,
    private val tan: Tan,
    functionName: String = "TrigonometricSystemFunction"
) : MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        val sinValue = sin.invoke(x, acc)
        val cscValue = csc.invoke(x, acc)
        val tanValue = tan.invoke(x, acc)

        return ((cscValue - sinValue) * cscValue) - (tanValue / cscValue)
    }
}
