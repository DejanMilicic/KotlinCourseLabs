package lab2.task1

import lab2.common.isEqualsTo


/**
 * Task 1: Find a Pair that adds to the Highest Sum
 *
 * Write an extension function for a list of ints that returns a pair of numbers from the list
 * that make the highest sum. If there is more than one pair that makes the highest sum,
 * return the last pair.
 *
 * Constraints:
 * List has at least two integers.
 * Items in the list are in range -1000..1000
 *
 */

internal fun List<Int>.findHighestSumPair(): Pair<Int, Int> {
    if(this.size < 2) throw Exception("Array must have at least two elements")
    if(this[0] !in -1000..1000 || this[1] !in -1000..1000){
        throw Exception("All elements must be in range [-1000..1000]")
    }
    var first = 0
    var second = 0
    if (this[0] > this[1]){
        first = this[0]
        second = this[1]
    }else{
        first = this[1]
        second = this[0]
    }

    for(i in 2 until this.size){
        if(this[i] !in -1000..1000){
            throw Exception("All elements must be in range [-1000..1000]")
        }
        if (this[i] >= first){
            second = first
            first = this[i]
        }
        else if(this[i] >= second){
            second = this[i]
        }
    }

    return Pair(second, first)
}

fun main() {
    val nums = listOf(743, 284, 677, -753, 995, -934, 102, 903, -83, -760, 77, -420)
    val expectedPair = Pair(995, 903)
    val actualPair = nums.findHighestSumPair()

    println("Pair that has highest sum in list $nums is $actualPair.")

    require(expectedPair.isEqualsTo(actualPair)) {
        "Actual pair that has highest sum in list $nums was $actualPair, but expected was $expectedPair"
    }
}
