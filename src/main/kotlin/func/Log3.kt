package com.example.func

class Log3(
    private val ln: Ln,
    functionName: String = "Log3"
) : MatFunction(functionName) {
    override fun invoke(x: Double, acc: Double): Double {
        return ln.invoke(x, acc) / ln.invoke(3.0, acc)
    }
}
