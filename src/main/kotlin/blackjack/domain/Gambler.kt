package blackjack.domain

class Gambler(name: String) : Participant(name) {
    override val isDealer: Boolean = false

    private var betAmount: Int = 0

    var profit: Double = 0.0
        private set

    val isLost: Boolean = profit < 0.0

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

    fun calculateProfit(dealer: Dealer) {
        val resultStatus = ResultStatus.of(this, dealer)
        profit = betAmount * determinePayoutRate(resultStatus)
    }

    private fun determinePayoutRate(resultStatus: ResultStatus): Double {
        return when (resultStatus) {
            ResultStatus.WIN -> if (isBlackjack()) {
                1.5
            } else {
                1.0
            }

            ResultStatus.DRAW -> 1.0
            ResultStatus.DEFEAT -> -1.0
        }
    }

}
