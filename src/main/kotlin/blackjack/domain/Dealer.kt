package blackjack.domain

class Dealer : Participant("딜러") {
    override fun isDealer() = true

    override fun canNotReceiveCard(): Boolean {
        return score > MAXIMUM_SCORE_TO_RECEIVE_CARD
    }

    fun determineResult(gamblerResults: List<GamblerResult>): DealerResult {
        val profit = gamblerResults.sumOf { gamblerResult -> gamblerResult.profit }
            .reverseSign()

        return DealerResult(this, profit)
    }

    private fun Double.reverseSign(): Double {
        return -this
    }

    companion object {
        const val MAXIMUM_SCORE_TO_RECEIVE_CARD = 16
    }
}
