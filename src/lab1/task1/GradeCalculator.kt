package lab1.task1

import java.util.Scanner

internal fun calculateGrade(score: Int): Int {
    if (score !in 0..100) {
        throw IllegalArgumentException("Score must be between 0 and 100")
    }

    return when (score) {
        in 91..100 -> 10
        in 81..90 -> 9
        in 71..80 -> 8
        in 61..70 -> 7
        in 51..60 -> 6
        else -> 5
    }
}

fun main() {
    print("Enter student score: ")
    val scanner = Scanner(System.`in`)
    try {
        val score = scanner.nextInt()
        val grade = calculateGrade(score)
        println("Grade: $grade")
    } catch (e: IllegalArgumentException) {
        println("Invalid score. Please enter a score between 0 and 100.")
    }
}