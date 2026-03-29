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
    if(x !in -1000000..1000000){
        throw IllegalArgumentException("Invalid number. The number must be between -1000000 and 1000000.")
    }
    var original = x
    var reversed = 0
    while (original != 0) {
        val digit = original % 10
        reversed = reversed * 10 + digit
        original /= 10
    }
    return reversed
}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
