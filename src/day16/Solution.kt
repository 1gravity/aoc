package day16

import println
import readInput

fun part1(input: List<String>): Long {
    return 0L
}

fun part2(input: List<String>): Long {
    return 0L
}

fun main() {
    val day = "16"
    val testInput = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput)
    check(resultPart1 == 0L) { "was $resultPart1" }
    part1(input).println()

    val resultPart2 = part2(testInput)
    check(resultPart2 == 0L) { "was $resultPart2" }
    part2(input).println()
}
