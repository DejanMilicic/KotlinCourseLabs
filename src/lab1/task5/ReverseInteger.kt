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
    val mathSign = if (x < 0) - 1 else 1
    var num = abs(x)
    var reverse = 0
    while (num > 0) {
        reverse = reverse * 10 + num % 10
        num = num / 10
    }
    return reverse * mathSign
}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
