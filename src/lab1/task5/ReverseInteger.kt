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
    var n = x
    var result = 0
    while (n > 0) {
        result = 10 * result + n % 10
        n = n / 10
    }
    return result
}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
