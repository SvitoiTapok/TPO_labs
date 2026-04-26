package com.example.func

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.double
import io.kotest.property.checkAll
import kotlin.math.*

class MatFunctionPropertyTest : FreeSpec({
    val accuracy = 1e-7
    val tolerance = 1e-4
    val sin = Sin()
    val cos = Cos(sin)
    val tan = Tan(sin, cos)
    val csc = Csc(sin)
    val ln = Ln()

    "Sin should match kotlin.math.sin" {
        checkAll(Arb.double(-10.0, 10.0)) { x ->
            sin.invoke(x, accuracy) shouldBe (sin(x) plusOrMinus tolerance)
        }
    }

    "Cos should match kotlin.math.cos" {
        checkAll(Arb.double(-10.0, 10.0)) { x ->
            cos.invoke(x, accuracy) shouldBe (cos(x) plusOrMinus tolerance)
        }
    }

    "Tan should match kotlin.math.tan away from asymptotes" {
        checkAll(Arb.double(-10.0, 10.0)) { x ->
            if (abs(cos(x)) > 0.05) {
                tan.invoke(x, accuracy) shouldBe (tan(x) plusOrMinus tolerance)
            }
        }
    }

    "Csc should match reciprocal of kotlin.math.sin away from zeroes" {
        checkAll(Arb.double(-10.0, 10.0)) { x ->
            if (abs(sin(x)) > 0.05) {
                csc.invoke(x, accuracy) shouldBe ((1 / sin(x)) plusOrMinus tolerance)
            }
        }
    }

    "Ln should match kotlin.math.ln for positive values" {
        checkAll(Arb.double(0.01, 100.0)) { x ->
            ln.invoke(x, accuracy) shouldBe (ln(x) plusOrMinus tolerance)
        }
    }

    "Log3 should match logarithm with base 3" {
        val log3 = Log3(ln)

        checkAll(Arb.double(0.01, 100.0)) { x ->
            log3.invoke(x, accuracy) shouldBe (log(x, 3.0) plusOrMinus tolerance)
        }
    }

    "Log5 should match logarithm with base 5" {
        val log5 = Log5(ln)

        checkAll(Arb.double(0.01, 100.0)) { x ->
            log5.invoke(x, accuracy) shouldBe (log(x, 5.0) plusOrMinus tolerance)
        }
    }

    "Log10 should match logarithm with base 10" {
        val log10 = Log10(ln)

        checkAll(Arb.double(0.01, 100.0)) { x ->
            log10.invoke(x, accuracy) shouldBe (log(x, 10.0) plusOrMinus tolerance)
        }
    }

    "Ln should return NaN outside its domain" {
        checkAll(Arb.double(-100.0, 0.0)) { x ->
            ln.invoke(x, accuracy).isNaN() shouldBe true
        }
    }

    "Logarithm identities should hold for generated positive values" {
        checkAll(Arb.double(0.01, 100.0)) { x ->
            val log3 = Log3(ln)
            val log5 = Log5(ln)
            val log10 = Log10(ln)

            log3.invoke(3.0.pow(x), accuracy) shouldBe (x plusOrMinus 1e-3)
            log5.invoke(5.0.pow(x / 2), accuracy) shouldBe ((x / 2) plusOrMinus 1e-3)
            log10.invoke(10.0.pow(x / 10), accuracy) shouldBe ((x / 10) plusOrMinus 1e-3)
        }
    }
})
