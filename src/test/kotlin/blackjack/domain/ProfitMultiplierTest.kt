package blackjack.domain

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ProfitMultiplierTest : FreeSpec({
    "배팅 금액에 곱할 배당률을 결정한다" - {
        "ResultStatus.WIN, isBlackjack=true 인 경우 1.5배를 반환" {
            ProfitMultiplier.determine(resultStatus = ResultStatus.WIN, isBlackjack = true) shouldBe 1.5
        }

        "ResultStatus.WIN, isBlackjack=false 인 경우 1.0배를 반환" {
            ProfitMultiplier.determine(resultStatus = ResultStatus.WIN, isBlackjack = false) shouldBe 1.0
        }

        "ResultStatus.DRAW인 경우, 블랙잭 여부와 관계 없이 1.0배를 반환" - {
            listOf(true, false).forEach { isBlackjack ->
                ProfitMultiplier.determine(resultStatus = ResultStatus.DRAW, isBlackjack = isBlackjack) shouldBe 1.0
            }
        }

        "ResultStatus.DEFEAT인경우, 블랙잭 여부와 관계 없이 -1.0배를 반환" - {
            listOf(true, false).forEach { isBlackjack ->
                ProfitMultiplier.determine(resultStatus = ResultStatus.DEFEAT, isBlackjack = isBlackjack) shouldBe -1.0
            }
        }
    }
})
