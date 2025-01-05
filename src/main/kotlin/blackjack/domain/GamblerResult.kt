package blackjack.domain

data class GamblerResult(
    val gambler: Gambler,
    val profit: Double,
) {
    fun isLost(): Boolean {
        return profit < 0.0
    }
}
