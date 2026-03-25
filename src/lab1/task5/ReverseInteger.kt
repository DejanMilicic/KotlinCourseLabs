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
    require(x in -1_000_000..1_000_000) {
        "Provided value is out of range."
    }

    var n = x
    val reverse = ArrayList<Int>()
    var isPositive = true;

    if (n < 0) {
        isPositive = false
    }

    n = abs(x)

    while (n > 0) {
        val remainder = n % 10;
        reverse.add(remainder)
        n /= 10
    }

    var c: Long = 0

    for (digit in reverse) {
        c = c * 10 + digit
    }

    return if (c > Int.MAX_VALUE) {
        0
    } else if (c < Int.MIN_VALUE) {
        0
    } else {
        if (isPositive) {
            c.toInt()
        } else {
            -c.toInt()
        }
    }
}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
