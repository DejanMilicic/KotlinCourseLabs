package lab1.task5

import kotlin.math.abs
import kotlin.require

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
    require(x <= 1000000 && x >= -1000000);
    var word = x.toString();
    if(word[0] == '-'){
        word = word.substring(1);
        return ("-" + word.reversed()).toInt();
    }
    return word.reversed().toInt();
}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
