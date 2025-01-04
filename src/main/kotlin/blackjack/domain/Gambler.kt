package blackjack.domain

class Gambler(name: String) : Participant(name) {
    var betAmount: Int = 0
        private set

    override fun isDealer(): Boolean {
        return false
    }

    override fun canNotReceiveCard(): Boolean {
        return score >= BlackjackRule.BLACKJACK_SCORE
    }

    fun placeBet(betAmount: Int) {
        require(betAmount > 0) { "배팅 금액은 0원을 초과해야 합니다. 현재 입력 = $betAmount" }
        this.betAmount = betAmount
    }

    fun determineResult(dealer: Dealer): GamblerResult {
        return GamblerResult(this, ResultStatus.of(this, dealer))
    }
}
