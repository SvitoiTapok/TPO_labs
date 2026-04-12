package task1

import com.example.task1.maclarenTan
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.double
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

class PBTest: FreeSpec({
    "Tangens" - {
        "should be symmetric"{
            Arb.double(-Math.PI/2+1e-6, Math.PI/2-1e-6).checkAll { a ->
                maclarenTan(a) shouldBe  (-maclarenTan(-a) plusOrMinus 1e-6)
            }
        }
        "should be periodical"{
            checkAll(
                Arb.int(-1000, 1000),
                Arb.double(-Math.PI / 2 + 0.01, Math.PI / 2 - 0.01)
            ) { a, b ->
                maclarenTan(b) shouldBe
                        (maclarenTan(b + a * Math.PI) plusOrMinus 1e-4)
            }
        }
    }
})