package blackjack.domain

class Dealer : Participant("딜러") {
    override fun isDealer(): Boolean {
        return true
    }

    override fun canNotReceiveCard(): Boolean {
        return score > MAXIMUM_SCORE_TO_RECEIVE_CARD
    }

    fun determineResult(gamblerResults: List<GamblerResult>): DealerResult {
        return DealerResult(this, gamblerResults)
    }

    companion object {
        const val MAXIMUM_SCORE_TO_RECEIVE_CARD = 16
    }
}
