package lab3.task2

import java.util.Stack

/**
 * Task 2: Split Expression To Parentheses Clusters
 *
 * Write a function that groups an expression of parentheses into parentheses cluster. Each cluster should be balanced.
 * If the expression isn't balanced, return the empty array.
 *
 * The following parenthesis pairs can appear in an expression string input: {}, [], ()
 *
 * The Cluster is considered balanced when every opening parenthesis must exist with its matching closing parens
 * in the same cluster.
 *
 * Examples:
 * ```"()()()"``` is split to the list: ```["()", "()", "()"]```
 * ```"((()))"``` is split to the list: ```["((()))"]```
 * ```"((()))(())()()(()())"``` is split to the list: ```["((()))", "(())", "()", "()", "(()())"]```
 * ```"((())())(()(()()))"``` is split to the list: ```["((())())", "(()(()()))"]```
 *
 * Constraints:
 * - All input strings will only contain parentheses.
 * - Input string can be empty
 *
 */

internal fun String.splitToBracketsClusters(): List<String> {
    val result = mutableListOf<String>()
    val stack = Stack<Char>()
    val current = StringBuilder()

    for (char in this) {
        current.append(char)

        when (char) {
            '(', '[', '{' -> stack.push(char)

            ')', ']', '}' -> {
                val expected = when (char) {
                    ')' -> '('
                    ']' -> '['
                    else -> '{'
                }

                if (stack.isEmpty() || stack.pop() != expected) {
                    return emptyList()
                }

                if (stack.isEmpty()) {
                    result.add(current.toString())
                    current.clear()
                }
            }
        }
    }

    if (stack.isNotEmpty()) {
        return emptyList()
    }

    return result
}

fun main() {
    val expressionsToClustersCatalog = mapOf(
        "()()()" to listOf("()", "()", "()"),
        "((()))" to listOf("((()))"),
        "((()))(())()()(()())" to listOf("((()))", "(())", "()", "()", "(()())"),
        "((())())(()(()()))" to listOf("((())())", "(()(()()))")
    )

    expressionsToClustersCatalog.forEach { (expression, expectedCluster) ->
        val actualClusters = expression.splitToBracketsClusters()
        require(expectedCluster == actualClusters) {
            "Expression $expression should be split to $expression clusters, but actual value was $actualClusters."
        }
    }
}
