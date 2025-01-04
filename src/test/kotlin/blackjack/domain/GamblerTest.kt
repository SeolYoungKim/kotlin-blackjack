package blackjack.domain

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

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
            listOf(-1, 0).forEach { betAmount ->
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

    "겜블러의 승패 결과를 판정할 수 있다" - {
        "딜러가 블랙잭이고 겜블러가 블랙잭이면 무승부다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.SPADES, Rank.TEN), Card(Suit.HEARTS, Rank.ACE))

            gambler.determineResult(dealer).resultStatus shouldBe ResultStatus.DRAW
        }

        "딜러가 블랙잭이고 겜블러가 블랙잭이 아니면 패배한다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.SPADES, Rank.TEN), Card(Suit.CLUBS, Rank.TEN), Card(Suit.DIAMONDS, Rank.ACE))

            gambler.determineResult(dealer).resultStatus shouldBe ResultStatus.DEFEAT
        }

        "딜러가 21점을 초과할 경우 겜블러의 카드에 상관 없이 겜블러가 무조건 승리한다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN), Card(Suit.DIAMONDS, Rank.TWO))

            val gambler1 = Gambler("kim")
            gambler1.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN), Card(Suit.DIAMONDS, Rank.TWO))

            val gambler2 = Gambler("lee")
            gambler2.receive(Card(Suit.HEARTS, Rank.TWO), Card(Suit.SPADES, Rank.TWO))

            assertSoftly {
                gambler2.determineResult(dealer).resultStatus shouldBe ResultStatus.WIN
                gambler1.determineResult(dealer).resultStatus shouldBe ResultStatus.WIN
            }
        }

        "딜러가 21점을 초과하지 않고, 겜블러가 21점을 초과하면 패배한다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN), Card(Suit.DIAMONDS, Rank.TWO))

            gambler.determineResult(dealer).resultStatus shouldBe ResultStatus.DEFEAT
        }

        "딜러가 21점이하이고, 겜블러가 딜러보다 점수가 낮으면 패배한다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN))

            gambler.determineResult(dealer).resultStatus shouldBe ResultStatus.DEFEAT
        }

        "딜러가 21점 이하이고, 겜블러가 딜러보다 점수가 높으면 승리한다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            gambler.determineResult(dealer).resultStatus shouldBe ResultStatus.WIN
        }

        "딜러가 21점 이하이고, 겜블러와 딜러의 점수가 동일하면 무승부다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN))

            gambler.determineResult(dealer).resultStatus shouldBe ResultStatus.DRAW
        }
    }
})
