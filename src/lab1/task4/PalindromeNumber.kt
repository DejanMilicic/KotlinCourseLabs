package lab1.task4

/**
 * Task 4: Given an integer x, return true if x is a palindrome, and false otherwise.
 *
 * Example 1:
 *```
 * Input: x = 121
 * Output: true
 * ```
 * Explanation: 121 reads as 121 from left to right and from right to left.
 * Example 2:
 *```
 * Input: x = -121
 * Output: false
 * ```
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore, it is not a palindrome.
 * Example 3:
 *```
 * Input: x = 10
 * Output: false
 * ```
 * Explanation: Reads 01 from right to left. Therefore, it is not a palindrome.
 *
 * Constraints:
 * ```
 * x in -1_000_000..1_000_000
 * ```
 */

internal fun isPalindrome(x: Int): Boolean {
    if (x <0) return false

    var br=x
    var ostatakBr=0

    while (br>0)
    {
        ostatakBr=ostatakBr*10+br%10
        br=br/10
    }

    return x==ostatakBr
}

fun main() {
    val number = 121
    if (isPalindrome(number)) {
        println("Number $number is a palindrome.")
    } else {
        println("Number $number is not a palindrome.")
    }
}