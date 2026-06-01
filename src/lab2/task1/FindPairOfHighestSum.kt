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
    require(size >= 2) {
        "List must contain at least two elements"
    }

    var highestPair = this[0] to this[1]
    var highestSum = this[0] + this[1]

    for (i in 0 until size - 1) {
        for (j in i + 1 until size) {
            val sum = this[i] + this[j]

            if (sum >= highestSum) {
                highestSum = sum
                highestPair = this[i] to this[j]
            }
        }
    }

    return highestPair
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
