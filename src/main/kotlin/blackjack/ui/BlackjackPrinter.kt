package blackjack.ui

import blackjack.domain.BlackjackResults
import blackjack.domain.BlackjackRule
import blackjack.domain.Dealer
import blackjack.domain.Gambler
import blackjack.domain.GamblerResult
import blackjack.domain.Participant
import blackjack.domain.Participants
import blackjack.domain.ResultStatus

object BlackjackPrinter {
    private const val PRINT_SEPARATOR = ", "
    private const val LINE_FEED = "\n"

    fun askForPlayerName() {
        println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)")
    }

    fun announceCardDistribution(participants: Participants) {
        val dealer = participants.extractDealer()
        val gamblerNames = participants.extractGamblers().joinToString(separator = PRINT_SEPARATOR) { gambler -> gambler.name }
        println("${dealer.name}, ${gamblerNames}에게 2장의 카드를 나누었습니다.")
    }

    fun printCardMessage(participants: Participants) {
        val dealer = participants.extractDealer()
        printCardMessage(dealer)

        participants.extractGamblers().forEach { gambler ->
            printCardMessage(gambler)
        }
        printLineFeed()
    }

    fun printCardMessage(participant: Participant) {
        println(createCardMessage(participant))
    }

    fun askIfWantMoreCard(name: String) {
        println("${name}은(는) 한장의 카드를 더 받겠습니까?(예는 ${BlackjackReader.YES_SIGN}, 아니오는 ${BlackjackReader.NO_SIGN}. 대소문자 구분 X)")
    }

    fun printAllFinalScore(participants: Participants) {
        val dealer = participants.extractDealer()
        println("${createCardMessage(dealer)} - 결과: ${dealer.score}")

        participants.extractGamblers().forEach { gambler ->
            println("${createCardMessage(gambler)} - 결과: ${gambler.score}")
        }
        printLineFeed()
    }

    private fun createCardMessage(participant: Participant) = "${participant.name}카드: ${CardMessageCreator.create(participant.cards)}"

    fun printLineFeed() {
        println()
    }

    fun announceCanNotReceiveCard(participant: Participant) {
        when (participant) {
            is Dealer -> println("딜러는 ${Dealer.MAXIMUM_SCORE_TO_RECEIVE_CARD}점을 초과하여 카드를 받을 수 없습니다.")
            is Gambler ->
                println(
                    """
                    |카드의 총합이 ${BlackjackRule.BLACKJACK_SCORE}을 초과하여 더이상 카드를 받을 수 없습니다.
                    |${participant.name}의 턴을 종료합니다.
                    """.trimMargin(),
                )
        }
        printLineFeed()
    }

    fun announceReceiveCard() {
        println("딜러는 ${Dealer.MAXIMUM_SCORE_TO_RECEIVE_CARD}점 이하라 한장의 카드를 더 받았습니다.")
        printLineFeed()
    }

    fun printWinOrDefeatResults(blackjackResults: BlackjackResults) {
        val dealerResult = blackjackResults.dealerResult
        val gamblerResultsMessage = createGamblerResultsMessage(blackjackResults)

        val resultsMessage =
            """
            |${dealerResult.dealer.name}: ${dealerResult.winCount}승 ${dealerResult.defeatCount}패 ${dealerResult.drawCount}무
            |$gamblerResultsMessage
            """.trimMargin()
        println(resultsMessage)
    }

    private fun createGamblerResultsMessage(blackjackResults: BlackjackResults): String {
        val gamblerResults = blackjackResults.gamblerResults
        return gamblerResults.joinToString(separator = LINE_FEED) { gamblerResult ->
            val gambler = gamblerResult.gambler
            "${gambler.name}: ${createWinOrDefeatMessage(gamblerResult)}"
        }
    }

    private fun createWinOrDefeatMessage(gamblerResult: GamblerResult): String {
        return when (gamblerResult.resultStatus) {
            ResultStatus.WIN -> "승"
            ResultStatus.DEFEAT -> "패"
            ResultStatus.DRAW -> "무"
        }
    }

    fun askBetAmount(gamblerName: String) {
        println("${gamblerName}의 배팅 금액은?")
    }
}
