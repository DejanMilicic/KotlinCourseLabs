package lab1.task3

import common.TestCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.readLines

class ScrabbleWordScoreCalculatorTest : TestCase() {

    @Test
    fun `test - calculate Scrabble score for word`() {
        val wordToScoreCatalog: Map<String, Int> = loadTestData()

        wordToScoreCatalog.forEach { (word, expectedScore) ->
            val actualScore = calculateWordScrabbleScore(word)

            assertEquals(
                expectedScore,
                actualScore,
                "Expected Scrabble score for a word $word is $expectedScore, but it was $actualScore."
            )
        }
    }

    private fun loadTestData() = getTestData("scrabble_words.csv")
        .readLines()
        // Drop header line
        .drop(1)
        .associate { line ->
            val (word, score) = line.split(",")

            word.trim() to score.trim().toInt()
        }
}