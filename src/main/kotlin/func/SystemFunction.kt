package com.example.func

class SystemFunction(
    private val trigonometricSystemFunction: TrigonometricSystemFunction,
    private val logarithmicSystemFunction: LogarithmicSystemFunction,
    functionName: String = "SystemFunction"
) : MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        return if (x <= 0.0) {
            trigonometricSystemFunction.invoke(x, acc)
        } else {
            logarithmicSystemFunction.invoke(x, acc)
        }
    }
}
