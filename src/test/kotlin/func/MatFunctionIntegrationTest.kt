package com.example.func

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.sin
import kotlin.math.tan

class MatFunctionIntegrationTest : FreeSpec({
    val accuracy = 1e-7
    val tolerance = 1e-4

    "trigonometric functions should work together with real dependencies" {
        val sinFunction = Sin()
        val cosFunction = Cos(sinFunction)
        val tanFunction = Tan(sinFunction, cosFunction)
        val cscFunction = Csc(sinFunction)
        val points = listOf(-PI / 3, -PI / 6, PI / 6, PI / 4, PI / 3)

        points.forEach { x ->
            sinFunction.invoke(x, accuracy) shouldBe (sin(x) plusOrMinus tolerance)
            cosFunction.invoke(x, accuracy) shouldBe (cos(x) plusOrMinus tolerance)
            tanFunction.invoke(x, accuracy) shouldBe (tan(x) plusOrMinus tolerance)
            cscFunction.invoke(x, accuracy) shouldBe ((1 / sin(x)) plusOrMinus tolerance)
        }
    }

    "logarithmic functions should work together with real Ln dependency" {
        val lnFunction = Ln()
        val log3Function = Log3(lnFunction)
        val log5Function = Log5(lnFunction)
        val log10Function = Log10(lnFunction)
        val points = listOf(0.1, 0.5, 1.0, 2.0, 3.0, 5.0, 10.0, 25.0)

        points.forEach { x ->
            lnFunction.invoke(x, accuracy) shouldBe (ln(x) plusOrMinus tolerance)
            log3Function.invoke(x, accuracy) shouldBe (log(x, 3.0) plusOrMinus tolerance)
            log5Function.invoke(x, accuracy) shouldBe (log(x, 5.0) plusOrMinus tolerance)
            log10Function.invoke(x, accuracy) shouldBe (log(x, 10.0) plusOrMinus tolerance)
        }
    }
})
