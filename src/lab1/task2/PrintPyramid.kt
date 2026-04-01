package lab1.task2

/**
 * Task 2: Pyramid Printing
 *
 * Write a Kotlin program that prints a pyramid structure to the console based on the specified level.
 * Constraint:
 * ```
 * level is in a range from 1 to 15
 * ```
 * The pyramid should be composed of asterisks (`*`) arranged in a pattern as shown below:
 * ```
 *     *
 *    ***
 *   *****
 *  *******
 * ```
 * The number of levels in the pyramid is determined by the `level` parameter passed to the function.
 * The `level` parameter must be an integer between 3 and 15 (inclusive).
 *
 * Your task is to implement a function called `printPyramid(level: Int)` that accepts an integer `level` as input
 * and prints the pyramid structure to the console. The function should ensure that the pyramid is symmetric
 * and properly aligned.
 *
 *
 * Example:
 * For `level = 3`, the output should be:
 * ```
 *   *
 *  ***
 * *****
 * ```
 *
 * Example:
 * For `level = 4`, the output should be:
 * ```
 *     *
 *    ***
 *   *****
 *  *******
 * ```
 * For `level = 5`, the output should be:
 * ```
 *     *
 *    ***
 *   *****
 *  *******
 * *********
 * ```
 *
 * Requirements:
 * - Implement the `printPyramid` function to generate the pyramid structure based on the given `level`.
 * - Ensure that the input `level` is an integer between 1 and 15 (inclusive). If the input is invalid,
 * throw an IllegalArgumentException exception.
 * - The pyramid should be symmetric and properly aligned.
 *
 */

internal fun printPyramid(level: Int) {
    if (level !in 1..15) {
        throw IllegalArgumentException("Level must be between 1 and 15 inclusive.")
    }
    val pyramidBuilder = StringBuilder()
    val maxWidth = 2 * level - 1

    for (i in 1..level) {
        val starCount = 2 * i - 1
        val sideSpaceCount = (maxWidth - starCount) / 2
        val spaces = " ".repeat(sideSpaceCount)
        val stars = "*".repeat(starCount)
        pyramidBuilder.append(spaces).append(stars).append(spaces)
        if (i < level) {
            pyramidBuilder.append("\n")
        }
    }
    print(pyramidBuilder.toString())
}

fun main() {
    try {
        println("Pyramid with level 5:")
        printPyramid(5)

        println("\nPyramid with level 3:")
        printPyramid(3)
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}