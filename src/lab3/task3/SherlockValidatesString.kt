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
    val charCounts = s.groupingBy { it }.eachCount()
    val charsPerFrequency = charCounts.values.groupingBy { it }.eachCount()

    val isValid = when (charsPerFrequency.size) {
        1 -> true
        2 -> {
            val (count1, count2) = charsPerFrequency.keys.toList()
            val (occurrences1, occurrences2) = charsPerFrequency.values.toList()
            (occurrences1 == 1 && (count1 == 1 || count1 - count2 == 1)) || (occurrences2 == 1 && (count2 == 1 || count2 - count1 == 1))
        }

        else -> false
    }

    return if (isValid) "YES" else "NO"
}

fun main() {
    val stringsToValidityCatalog = mapOf("abc" to "YES", "abcc" to "YES", "abccc" to "NO")

    stringsToValidityCatalog.forEach { (string, expectedIsValid) ->
        val actualIsValid = isSherlockValid(string)
        val errorMessageFactory = { answer: String -> if (answer == "YES") "is valid" else "is not valid" }

        require(expectedIsValid == actualIsValid) {
            "String \"$string\" is ${errorMessageFactory(expectedIsValid)}," +
                    " but actual value was ${errorMessageFactory(actualIsValid)}."
        }
    }
}
