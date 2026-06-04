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
    if (x !in -1_000_000..1_000_000)
    {
        throw ArithmeticException()
    }

    var i = x
    var reverse = 0
    var tmp = x

    while (tmp != 0 ) {
        val digit = tmp % 10
        reverse = reverse * 10 + digit
        tmp /= 10

    }

    return reverse
}

fun main() {
    val integer = 120
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
