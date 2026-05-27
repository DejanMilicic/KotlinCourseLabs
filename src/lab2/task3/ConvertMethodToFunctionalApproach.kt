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
    return indices.asSequence().flatMap { i -> indices.asSequence().drop(i + 1).map{j->this[i] to this[j]} }
        .reduce { bestPair, currentPair ->
            val currentDiff = abs(currentPair.first - currentPair.second)
            val bestDiff = abs(bestPair.first - bestPair.second)
            if (currentDiff >= bestDiff)
                currentPair else bestPair
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
