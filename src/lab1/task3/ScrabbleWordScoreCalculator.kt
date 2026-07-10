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
    var i: Int = 0
    val one: String = "AEIOULNRST"
    val two:String = "DG"
    val three:String = "BCMP"
    val four:String = "FHVWY"
    val five:String = "K"
    val eight:String = "JX"
    val ten:String = "QZ"
    var score:Int = 0
    while(i < word.length) {
        if(word[i].uppercaseChar() in one){
            score += 1;
        }
        if(word[i].uppercaseChar() in two){
            score += 2;
        }
        if(word[i].uppercaseChar() in three){
            score += 3;
        }
        if(word[i].uppercaseChar() in four){
            score += 4;
        }
        if(word[i].uppercaseChar() in five){
            score += 5;
        }
        if(word[i].uppercaseChar() in eight){
            score += 8;
        }
        if(word[i].uppercaseChar() in ten) {
            score += 10;
        }
        i++
    }
    return score
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