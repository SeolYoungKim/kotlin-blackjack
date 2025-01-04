package blackjack.domain

enum class ResultStatus {
    WIN,
    DRAW,
    DEFEAT, ;

    companion object {
        fun of(gambler: Gambler, dealer: Dealer): ResultStatus {
            return when {
                dealer.isBlackjack() && gambler.isNotBlackjack() -> DEFEAT
                dealer.isBurst() -> WIN
                gambler.isBurst() -> DEFEAT
                gambler.isScoreLargerThan(dealer) -> WIN
                gambler.isScoreEqualTo(dealer) -> DRAW
                else -> DEFEAT
            }
        }
    }
}
