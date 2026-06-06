package lab6.taskProjections.task4

/**
 *  Projections #4
 *
 * Implement a simple function copy,
 * that accepts two arrays with a generic type of Any and copy elements from the first array to the second one.
 **/
fun copy(a: Array<out Any>, b: Array<in Any>): Unit {
    for (i in 0 until a.size) {
        b[i] = a[i]
    }
}


fun main() {
    val ints = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    ints.forEach { print("$it ") }
    println()
    any.forEach { print("$it ") }
    println()
    println("_______")
    println("_______")


    copy(ints, any)

    ints.forEach { print("$it ") }
    println()
    any.forEach { print("$it ") }
}
