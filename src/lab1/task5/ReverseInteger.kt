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

    var xx = x
    var result = 0
    while (xx != 0){
        result *= 10
        val helper = xx % 10
        result += helper
        xx /= 10
    }

    return result

}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
