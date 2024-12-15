package blackjack.domain

class Gambler(name: String) : Player(name) {
    override fun canNotReceiveCard(): Boolean {
        val totalScore = calculateTotalScore()
        return totalScore >= BlackJackRule.WIN_SCORE
    }
}
