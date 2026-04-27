package com.example.func

class LogarithmicSystemFunction(
    private val ln: Ln,
    private val log3: Log3,
    private val log5: Log5,
    private val log10: Log10,
    functionName: String = "LogarithmicSystemFunction"
) : MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        val lnValue = ln.invoke(x, acc)
        val log3Value = log3.invoke(x, acc)
        val log5Value = log5.invoke(x, acc)
        val log10Value = log10.invoke(x, acc)

        return ((((log10Value + log3Value) / lnValue) - log10Value) +
            (log10Value - (log5Value * log3Value))) / log10Value
    }
}
