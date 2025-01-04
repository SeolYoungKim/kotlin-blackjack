package blackjack.domain

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ResultStatusTest : FreeSpec({
    "겜블러의 승패 결과를 판정할 수 있다" - {
        "딜러가 블랙잭이고 겜블러가 블랙잭이면 무승부다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.SPADES, Rank.TEN), Card(Suit.HEARTS, Rank.ACE))

            ResultStatus.of(gambler, dealer) shouldBe ResultStatus.DRAW
        }

        "딜러가 블랙잭이고 겜블러가 블랙잭이 아니면 패배한다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.SPADES, Rank.TEN), Card(Suit.CLUBS, Rank.TEN), Card(Suit.DIAMONDS, Rank.ACE))

            ResultStatus.of(gambler, dealer) shouldBe ResultStatus.DEFEAT
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

            ResultStatus.of(gambler, dealer) shouldBe ResultStatus.DEFEAT
        }

        "딜러가 21점이하이고, 겜블러가 딜러보다 점수가 낮으면 패배한다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN))

            ResultStatus.of(gambler, dealer) shouldBe ResultStatus.DEFEAT
        }

        "딜러가 21점 이하이고, 겜블러가 딜러보다 점수가 높으면 승리한다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.ACE))

            ResultStatus.of(gambler, dealer) shouldBe ResultStatus.WIN
        }

        "딜러가 21점 이하이고, 겜블러와 딜러의 점수가 동일하면 무승부다" {
            val dealer = Dealer()
            dealer.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN))

            val gambler = Gambler("kim")
            gambler.receive(Card(Suit.HEARTS, Rank.TEN), Card(Suit.SPADES, Rank.TEN))

            ResultStatus.of(gambler, dealer) shouldBe ResultStatus.DRAW
        }
    }
})
