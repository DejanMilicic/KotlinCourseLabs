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
    if (this.isEmpty()) return emptyList()

    val clusters = mutableListOf<String>()
    val stack = Stack<Char>()
    val currentCluster = StringBuilder()
    val pairs = mapOf(')' to '(', ']' to '[', '}' to '{')

    for (char in this) {
        currentCluster.append(char)
        if (char in listOf('(', '{', '[')) {
            stack.addLast(char)
        } else {
            if (stack.isEmpty() || stack.removeLast() != pairs[char]) {
                return emptyList()
            }
        }
        if (stack.isEmpty()) {
            clusters.add(currentCluster.toString())
            currentCluster.clear()
        }
    }

    return if (stack.isEmpty()) clusters else emptyList()
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
