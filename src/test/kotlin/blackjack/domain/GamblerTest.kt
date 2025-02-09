package blackjack.domain

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class GamblerTest : FreeSpec({
    "카드를 한장 받을 수 있다" {
        val gambler = Gambler("타짜")
        val card = Card(Suit.CLUBS, Rank.SIX)

        gambler.receive(card)

        gambler.cards shouldHaveSize 1
        gambler.cards[0] shouldBe card
    }

    "카드를 여러장 받을 수 있다" {
        val gambler = Gambler("타짜")
        val cards =
            arrayOf(
                Card(Suit.HEARTS, Rank.SIX),
                Card(Suit.SPADES, Rank.SIX),
                Card(Suit.DIAMONDS, Rank.SIX),
                Card(Suit.CLUBS, Rank.SIX),
            )

        gambler.receive(*cards)

        gambler.cards shouldHaveSize 4
        gambler.cards shouldBe cards
    }

    "총합이 21 이상이면 카드를 받을 수 없다" - {
        "카드를 받을 수 있는 경우" {
            val gambler = Gambler("타짜")
            gambler.receive(
                Card(Suit.HEARTS, Rank.NINE),
                Card(Suit.SPADES, Rank.ACE),
            )

            gambler.canNotReceiveCard() shouldBe false
        }

        "카드를 받을 수 없는 경우" {
            val gambler = Gambler("타짜")
            gambler.receive(
                Card(Suit.HEARTS, Rank.TEN),
                Card(Suit.SPADES, Rank.ACE),
            )

            gambler.canNotReceiveCard() shouldBe true
        }
    }

    "배팅 금액은 0원을 초과해야 한다" - {
        "0원을 초과하지 않는 경우 예외를 던진다" - {
            listOf(-1L, 0L).forEach { betAmount ->
                "betAmount: $betAmount" {
                    val gambler = Gambler("kim")
                    shouldThrow<IllegalArgumentException> { gambler.placeBet(betAmount) }
                }
            }
        }

        "0원을 초과하는 경우 예외가 발생하지 않는다" {
            val gambler = Gambler("kim")
            shouldNotThrowAny { gambler.placeBet(1) }
        }
    }

    "결과에 따라 GamblerResult를 만든다" - {
        "딜러를 이기고 블랙잭인 경우" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.CLUBS, Rank.TWO), Card(Suit.CLUBS, Rank.THREE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.DIAMONDS, Rank.ACE), Card(Suit.DIAMONDS, Rank.TEN))
            gambler.placeBet(1000)

            val gamblerResult = gambler.determineResult(dealer)
            gamblerResult.profit shouldBe BigDecimal("1500.0")
        }

        "딜러를 이겼지만 블랙잭은 아닌 경우" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.CLUBS, Rank.TWO), Card(Suit.CLUBS, Rank.THREE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.DIAMONDS, Rank.TWO), Card(Suit.DIAMONDS, Rank.TEN))
            gambler.placeBet(1000)

            val gamblerResult = gambler.determineResult(dealer)
            gamblerResult.profit shouldBe BigDecimal("1000.0")
        }

        "딜러와 무승부인 경우" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.CLUBS, Rank.TWO), Card(Suit.CLUBS, Rank.THREE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.DIAMONDS, Rank.TWO), Card(Suit.DIAMONDS, Rank.THREE))
            gambler.placeBet(1000)

            val gamblerResult = gambler.determineResult(dealer)
            gamblerResult.profit shouldBe BigDecimal("1000.0")
        }

        "딜러에게 진 경우" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.CLUBS, Rank.TWO), Card(Suit.CLUBS, Rank.THREE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.DIAMONDS, Rank.TWO), Card(Suit.DIAMONDS, Rank.TWO))
            gambler.placeBet(1000)

            val gamblerResult = gambler.determineResult(dealer)
            gamblerResult.profit shouldBe BigDecimal("-1000.0")
        }
    }
})
