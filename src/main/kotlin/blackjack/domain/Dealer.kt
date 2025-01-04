package blackjack.domain

import blackjack.util.reverseSign

class Dealer : Participant("딜러") {
    override val isDealer: Boolean = true

    var profit: Double = 0.0
        private set

    override fun canNotReceiveCard(): Boolean {
        return score > MAXIMUM_SCORE_TO_RECEIVE_CARD
    }

    fun determineResult(gamblerResults: List<GamblerResult>): DealerResult {
        return DealerResult(this, gamblerResults)
    }

    fun calculateProfit(gamblers: List<Gambler>) {
        profit = gamblers.filter { gambler -> gambler.isLost }
            .sumOf { gambler -> gambler.profit }
            .reverseSign()
    }

    companion object {
        const val MAXIMUM_SCORE_TO_RECEIVE_CARD = 16
    }
}
