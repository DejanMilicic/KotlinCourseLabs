package lab1.task3

/**
 * Task 3:
 * In Scrabble, each letter has a corresponding point value. To calculate the score of a word,
 * you sum up the point values of each letter in the word according to the following table:
 *
 * Score Table:
 * ```
 * 1  Point:  A, E, I, O, U, L, N, R, S, T
 * 2  Points: D, G
 * 3  Points: B, C, M, P
 * 4  Points: F, H, V, W, Y
 * 5  Points: K
 * 8  Points: J, X
 * 10 Points: Q, Z
 *```
 * For example, the word 'faculty' has a Scrabble score of 15, as the following mappings apply:
 * ```
 *    'f' = 4
 *    'a' = 1
 *    'c' = 3
 *    'u' = 1
 *    'l' = 1
 *    't' = 1
 *    'y' = 4
 *  Total: 15
 *```
 * Assignment: Implement the [calculateWordScrabbleScore] function, so that it returns the Scrabble score for a given
 * [word]. It is guaranteed that the [word] contains only characters in the range from 'a' to 'z'.
 *
 */
internal fun calculateWordScrabbleScore(word: String): Int {
    val score=mapOf(
        'a' to 1,'e' to 1,'i' to 1,'o' to 1,'u' to 1,
        'l' to 1, 'n' to 1, 'r' to 1, 's' to 1, 't' to 1,
        'd' to 2, 'g' to 2,
        'b' to 3, 'c' to 3, 'm' to 3, 'p' to 3,
        'f' to 4, 'h' to 4, 'v' to 4, 'w' to 4, 'y' to 4,
        'k' to 5,
        'j' to 8, 'x' to 8,
        'q' to 10, 'z' to 10
    )
    return word.sumOf { score[it] ?: 0 }
}

fun main() {
    val word = "faculty"
    val actualScore = calculateWordScrabbleScore(word)
    val expectedScore = 15
    if (actualScore == expectedScore) {
        println("Well done!")
    } else {
        println("Score for a given word $word is $actualScore, but it should be 15")
    }
}