package day11

import println
import readInput

fun solve(input: List<String>): Long {
    return 0
}

fun part1(input: List<String>): Long {
    return solve(input)
}

fun part2(input: List<String>): Long {
    return solve(input)
}

fun main() {
    val day = "11"
    val testInput = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput)
    check(resultPart1 == 0L) { "was $resultPart1" }
    part1(input).println()

    val resultPart2 = part2(testInput)
    check(resultPart2 == 0L) { "was $resultPart2" }
    part2(input).println()
}
