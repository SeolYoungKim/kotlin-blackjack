package blackjack.domain

class Gambler(name: String) : Participant(name) {
    private var betAmount: Double = 0.0

    override fun isDealer() = false

    override fun canNotReceiveCard(): Boolean {
        return score >= BlackjackRule.BLACKJACK_SCORE
    }

    fun placeBet(betAmount: Double) {
        require(betAmount > 0) { "배팅 금액은 0원을 초과해야 합니다. 현재 입력 = $betAmount" }
        this.betAmount = betAmount
    }

    fun determineResult(dealer: Dealer): GamblerResult {
        val resultStatus = ResultStatus.of(this, dealer)
        val profit = betAmount * ProfitMultiplier.determine(resultStatus, isBlackjack())
        return GamblerResult(this, profit)
    }
}
