package com.github.alxmag.loremipsumgenerator.util

interface UnitConverter {

    fun convertTo(amount: Int, textAmountUnit: TextAmountUnit): MinMax
}

class WordsConverter(
    private val wordsPerSentence: MinMax,
    private val sentencesPerParagraph: MinMax
) : UnitConverter {

    override fun convertTo(amount: Int, textAmountUnit: TextAmountUnit): MinMax = when (textAmountUnit) {
        TextAmountUnit.WORD -> MinMax(amount)

        TextAmountUnit.SENTENCE -> MinMax(amount)
            .map(wordsPerSentence, ::divNumsRoundUp)

        TextAmountUnit.PARAGRAPH -> MinMax(amount)
            .map(wordsPerSentence, ::divNumsRoundUp)
            .map(sentencesPerParagraph, ::divNumsRoundUp)

        else -> throw UnsupportedOperationException()
    }
}

class SentencesConverter(
    private val wordsPerSentence: MinMax,
    private val sentencesPerParagraph: MinMax
) : UnitConverter {

    override fun convertTo(amount: Int, textAmountUnit: TextAmountUnit): MinMax = when (textAmountUnit) {
        TextAmountUnit.WORD -> MinMax(amount).map(wordsPerSentence, ::multNums)
        TextAmountUnit.SENTENCE -> MinMax(amount)
        TextAmountUnit.PARAGRAPH -> MinMax(amount).map(sentencesPerParagraph, ::divNumsRoundUp)
        else -> throw UnsupportedOperationException()
    }

}

private fun divNumsRoundUp(first: Int, second: Int) = first divRoundUp second
private fun multNums(first: Int, second: Int) = first * second

/**
 * Divides two integers with rounding result up
 */
private infix fun Int.divRoundUp(other: Int) = (this + other - 1) / other