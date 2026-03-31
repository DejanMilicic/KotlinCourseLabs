package lab1.task5

import kotlin.Exception


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
        throw Exception("X is not in range")

    var temp = x
    var result = 0
    while (temp != 0) {
        result *= 10
        result += temp % 10
        temp /= 10
    }

    return result

}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
