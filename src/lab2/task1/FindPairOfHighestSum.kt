package lab2.task1

import lab2.common.isEqualsTo


/**
 * Task 1: Find a Pair that adds to the Highest Sum
 *
 * Write an extension function for a list of ints that returns a pair of numbers from the list
 * that make the highest sum. If there is more than one pair that makes the highest sum,
 * return the last pair.
 *
 * Constraints:
 * List has at least two integers.
 * Items in the list are in range -1000..1000
 *
 */

internal fun List<Int>.findHighestSumPair(): Pair<Int, Int> {
    require(size >= 2) { "List must have at least two integers" }
    require(all { it in -1000..1000 }) { "All items must be in range -1000..1000" }

    var highestSum = Int.MIN_VALUE
    var bestFirst = 0
    var bestSecond = 0

    for (i in 0..< size-1){
        val firstNumber = this[i]
        for(j in i + 1..<size){
            val secondNumber = this[j]
            if (firstNumber + secondNumber > highestSum) {
                highestSum = firstNumber + secondNumber
                bestFirst = firstNumber
                bestSecond = secondNumber
            }
        }
    }

    return Pair(bestFirst, bestSecond)
}

fun main() {
    val nums = listOf(743, 284, 677, -753, 995, -934, 102, 903, -83, -760, 77, -420)
    val expectedPair = Pair(995, 903)
    val actualPair = nums.findHighestSumPair()

    println("Pair that has highest sum in list $nums is $actualPair.")

    require(expectedPair.isEqualsTo(actualPair)) {
        "Actual pair that has highest sum in list $nums was $actualPair, but expected was $expectedPair"
    }
}
