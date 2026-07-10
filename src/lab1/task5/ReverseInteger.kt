package lab1.task5

import kotlin.math.abs
import kotlin.math.pow


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
    if((x<-1000000) or (x>1000000)) {
        throw IllegalArgumentException("Number out of problem declared range!")
    }
    var result = 0
    var number:Int = abs(x)
    val len = abs(x).toString().length
    var i: Int = 10.00.pow(len-1).toInt()
    while(number > 0){
        val digit = number % 10
        if(x < 0){ result -= digit*i} else result += digit*i
        number /= 10
        i/=10
    }
    return result
}

fun main() {
    val integer = -321
    println("Reverse integer of number $integer is ${reverseInteger(integer)}")
}
