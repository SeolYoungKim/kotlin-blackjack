package blackjack.ui

object ConsoleReader {
    fun readLine(): String {
        return readlnOrNull()
            ?: throw IllegalArgumentException("null을 입력할 수 없습니다.")
    }

    fun readNumber(): Double {
        return readLine().toDoubleOrNull()
            ?: throw IllegalArgumentException("숫자만 입력해야 합니다.")
    }
}
