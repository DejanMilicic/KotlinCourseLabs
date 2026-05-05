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
    val result = arrayListOf<String>()
    val stack = Stack<Char>()
    var start = 0
    for (i in 0 until length) {
        if (this[i] == '(' || this[i] == '[' || this[i] == '{') {
            stack.push(this[i])
        } else if (this[i] == ')' || this[i] == ']' || this[i] == '}') {
            if (stack.isEmpty())
                return emptyList()
            val b = stack.pop()
            if ((this[i] == ')' && b != '(') || (this[i] == ']' && b != '[') || (this[i] == '}' && b != '{'))
                return emptyList()
        } else return emptyList()

        if (stack.isEmpty()) {
            result.add(this.substring(start, i + 1))
            start = i + 1
        }
    }

    return if (stack.isEmpty())
        result
    else emptyList()
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
