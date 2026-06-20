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
    val map = hashMapOf<Char, Int>()
    val mapNumber = hashMapOf<Int, Int>()

    for (char in s) {
        map[char] = map.getOrDefault(char, 0) + 1
    }

    for (num in map.values) {
        mapNumber[num] = mapNumber.getOrDefault(num, 0) + 1
        if (mapNumber.size > 2) {
            return "NO"
        }
    }

    if (mapNumber.size <= 1) {
        return "YES"
    }

    val n1 = mapNumber.entries.first()
    val n2 = mapNumber.entries.last()

    if ((n1.key == 1 && n1.value == 1) || (n2.key == 1 && n2.value == 1)) {
        return "YES"
    }
    if (n1.key == n2.key + 1 && n1.value == 1) {
        return "YES"
    }
    if (n2.key == n1.key + 1 && n2.value == 1) {
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
            "String \"$string\" is ${errorMessageFactory(expectedIsValid)}," +
                    " but actual value was ${errorMessageFactory(actualIsValid)}."
        }
    }
}
