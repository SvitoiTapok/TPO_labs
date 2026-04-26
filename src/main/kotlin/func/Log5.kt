package com.example.func

class Log5(
    private val ln: Ln,
    functionName: String = "Log5"
) : MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        return ln.invoke(x, acc) / ln.invoke(5.0, acc)
    }
}
