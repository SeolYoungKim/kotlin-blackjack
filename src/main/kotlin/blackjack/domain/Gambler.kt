package blackjack.domain

class Gambler(name: String) : Participant(name) {
    override fun isDealer(): Boolean {
        return false
    }

    override fun canNotReceiveCard(): Boolean {
        return score >= BlackjackRule.BLACKJACK_SCORE
    }

    fun determineResult(dealer: Dealer): GamblerResult {
        return GamblerResult(this, determineResultStatus(dealer))
    }

    private fun determineResultStatus(dealer: Dealer): ResultStatus {
        return when {
            dealer.isBlackjack() && this.isNotBlackjack() -> ResultStatus.DEFEAT
            dealer.isBurst() -> ResultStatus.WIN
            this.isBurst() -> ResultStatus.DEFEAT
            this.isScoreLargerThan(dealer) -> ResultStatus.WIN
            this.isScoreEqualTo(dealer) -> ResultStatus.DRAW
            else -> ResultStatus.DEFEAT
        }
    }
}
