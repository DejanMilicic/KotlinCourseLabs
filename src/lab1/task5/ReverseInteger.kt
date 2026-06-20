package lab1.task5

import kotlin.math.abs

/**
 * Task 5: Given an integer x, return x with its digits reversed.
 *
 * Constraints:
 *```
 * x in -1_000_000..1_000_000
 * ```
 *
 * Example 1:
 *```
 * Input: x = 123
 * Output: 321
 * ```
 * Example 2:
 *```
 * Input: x = -123
 * Output: -321
 * ```
 * Example 3:
 *```
 * Input: x = 120
 * Output: 21
 *```
 *
 */

internal fun reverseInteger(x: Int): Int {
    if (x !in -1_000_000..1_000_000) {
        throw IllegalArgumentException("Invalid number. Must be between -1_000_000 and 1_000_000.")
    }

    var number = x
    var reversed = 0
    while (number != 0) {
        reversed = reversed * 10 + number % 10
        number /= 10
    }
    return reversed
}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
