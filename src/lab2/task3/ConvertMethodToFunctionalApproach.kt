package lab2.task3

import lab2.common.isEqualsTo
import kotlin.math.abs

/**
 * Task 3: Function [findPairWithBiggestDifference] finds the pair with the biggest difference in a list of integers.
 *
 * The function is written with the imperative approach, and the task of this assignment is to refactor it to the
 * functional approach.
 *
 * Constraints:
 * List has at least two integers.
 *
 * @receiver The input list of integers from which to find the pair with the biggest difference.
 * @return The pair with the biggest difference between its elements.
 */

internal fun List<Int>.findPairWithBiggestDifference(): Pair<Int, Int> {
    if (this.size < 2) {
        throw IllegalArgumentException("List is too small")
    }
    return this.foldIndexed(this[0] to this[0]) { index, acc: Pair<Int, Int>, i ->
        if (index == 0) {
            acc
        } else {
            when {
                i > acc.first && i > acc.second -> {
                    if (acc.first > acc.second) acc.second to i else acc.first to i
                }

                i < acc.first && i < acc.second -> {
                    if (acc.first > acc.second) acc.first to i else acc.second to i
                }

                else -> acc
            }
        }
    }
}

fun main() {
    val nums = listOf(743, 284, 677, -753, 995, -934, 102, 903, -83, -760, 77, -420)
    val expectedPair = Pair(995, -934)
    val actualPair = nums.findPairWithBiggestDifference()

    println("Pair that has highest difference in list $nums is $actualPair")

    requireNotNull(actualPair)
    require(expectedPair.isEqualsTo(actualPair)) {
        "Actual pair that has highest difference in list $nums was $actualPair, but expected was $expectedPair."
    }
}
