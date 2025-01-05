package blackjack.domain

object ProfitMultiplier {
    fun determine(resultStatus: ResultStatus, isBlackjack: Boolean): Double {
        return when (resultStatus) {
            ResultStatus.WIN -> if (isBlackjack) {
                1.5
            } else {
                1.0
            }

            ResultStatus.DRAW -> 1.0
            ResultStatus.DEFEAT -> -1.0
        }
    }
}
