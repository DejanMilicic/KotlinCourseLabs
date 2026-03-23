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
    if (x < -1000000 || x > 1000000) throw IllegalArgumentException("x can't be < -1 000 000 or > 1 000 000 ")
    var datBroj = x
    var noviBroj = 0
    while(datBroj != 0){
        noviBroj = datBroj % 10 + (noviBroj * 10)
        datBroj /= 10
    }
    return noviBroj
}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
