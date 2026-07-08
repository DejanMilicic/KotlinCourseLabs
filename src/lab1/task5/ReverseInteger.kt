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
        "x must be in range -1_000_000..1_000_000"
    }

    var tmp = x
    var reversed = 0

    while (tmp != 0) {
        val digit = tmp % 10
        tmp /= 10
        reversed = reversed * 10 + digit
    }

    return reversed
}


fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")

//    val reverseInteger = integer.toString().reversed()
//    println("Reverse integer of number $integer is $reverseInteger")

}
