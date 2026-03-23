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
    val jedanPoen = listOf<Char>('A','E','I','O','U','L','N','R','S','T')
    val dvaPoena = listOf<Char>('D','G')
    val triPoena = listOf<Char>('B', 'C', 'M', 'P')
    val cetiriPoena = listOf<Char>('F', 'H', 'V', 'W', 'Y')
    val petPoena = listOf<Char>('K')
    val osamPoena = listOf<Char>('J', 'X')
    val desetPoena = listOf<Char>('Q', 'Z')

    val duzinaStringa = word.length
    var poeni = 0;

    for( i in 0..duzinaStringa-1){
        val slovo : Char = word[i].uppercaseChar()
        when(slovo){
            in jedanPoen -> poeni += 1;
            in dvaPoena -> poeni += 2;
            in triPoena -> poeni += 3;
            in cetiriPoena -> poeni += 4;
            in petPoena -> poeni += 5;
            in osamPoena -> poeni += 8;
            in desetPoena -> poeni += 10;
        }
    }

    return poeni;
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