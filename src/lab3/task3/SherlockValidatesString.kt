package lab3.task3

/**
 * Task 3: Sherlock Validates the Words
 *
 * Sherlock considers a string to be valid if all characters of the string appear the same number of times.
 * It is also valid if he can remove just a single character at index in the string,
 * and the remaining characters will occur the same number of times.
 *
 * Given a string, determine if it is valid. If so, return `YES`, otherwise return `NO`.
 *
 * Example:
 *```
 * s = abc
 * This is a valid string because frequencies are {`a`: 1, `b`: 1, `c`: 1} .
 *
 * s = abcc
 * This is a valid string because we can remove one `c` and have `1` of each character in the remaining string.
 *
 * s = abccc
 * This string is not valid as we can only remove `1` occurrence of `c`. That leaves character frequencies of
 * {`a`: 1, `b`: 1, `c`: 2}.
 * ```
 *
 * Constraints:
 * - `s` length is in range 1..10^5
 * - Each character of `s` is in a..z range
 *
 */

internal fun isSherlockValid(s: String): String {
    if (s.length !in 1..100_000)
        throw IllegalArgumentException("Invalid sherlock expression")

    for (c in s) {
        if (c !in 'a'..'z') {
            throw IllegalArgumentException("Invalid sherlock expression")
        }
    }

    val frequencyMap = s.groupingBy { it }.eachCount()
    val minVal = frequencyMap.minWith { entry1, entry2 -> entry1.value - entry2.value }.value
    val maxVal = frequencyMap.maxWith { entry1, entry2 -> entry1.value - entry2.value }.value


    var maxCnt = 0
    var minCnt = 0
    for (f in frequencyMap) {
        if (f.value == maxVal) {
            maxCnt++
        } else if (f.value == minVal) {
            minCnt++
        }
    }

    if (
        (maxCnt == 1 && maxVal - minVal == 1 && minCnt == frequencyMap.size - 1) ||
        (minVal == 1 && minCnt == 1 && maxCnt == frequencyMap.size - 1) ||
        maxVal == minVal
    ) {
        return "YES"
    }
    return "NO"

}

fun main() {
    val stringsToValidityCatalog = mapOf("abc" to "YES", "abcc" to "YES", "abccc" to "NO")

    stringsToValidityCatalog.forEach { (string, expectedIsValid) ->
        val actualIsValid = isSherlockValid(string)
        val errorMessageFactory = { answer: String -> if (answer == "YES") "is valid" else "is not valid" }

        require(expectedIsValid == actualIsValid) {
            "String \"$string\" ${errorMessageFactory(expectedIsValid)}," +
                    " but actual value was ${errorMessageFactory(actualIsValid)}."
        }
    }
}
