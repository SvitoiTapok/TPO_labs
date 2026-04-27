package com.example.func

class Log10(
    private val ln: Ln,
    functionName: String = "Log10"
) : MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        return ln.invoke(x, acc) / ln.invoke(10.0, acc)
    }
}
