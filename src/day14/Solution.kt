package day14

import println
import readInput

fun part1(input: List<String>): Int {
    return 0
}

fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val day = "14"
    val testInput = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput)
    check(resultPart1 == 0) { "was $resultPart1" }
    part1(input).println()

    val resultPart2 = part2(testInput)
    check(resultPart2 == 0) { "was $resultPart2" }
    part2(input).println()
}
