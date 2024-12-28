package blackjack.ui

import blackjack.domain.Card
import blackjack.domain.Rank
import blackjack.domain.Suit

object CardMessageCreator {
    private const val SEPARATOR = ", "

    fun create(cards: List<Card>): String {
        return cards
            .joinToString(separator = SEPARATOR) { card -> convertToSignature(card) }
    }

    private fun convertToSignature(card: Card): String {
        return "${card.rank.signature}${card.suit.signature}"
    }
}
