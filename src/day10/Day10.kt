package day10

import println
import readInput

data class Pos(val x: Int, val y: Int)

fun getPositions(input: List<String>): Array<MutableList<Pos>> {
    val positions = Array(10) { mutableListOf<Pos>() }
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            val nr = c.code - '0'.code
            positions[nr].add(Pos(x, y))
        }
    }
    return positions
}

val deltas = listOf(0 to -1, 0 to 1, 1 to 0, -1 to 0)
fun traverse(positions: Array<MutableList<Pos>>, nr: Int, pos: Pos, visited: MutableList<Pos>, allowDuplicates: Boolean): Long {
    if (nr == 9) {
        return when {
            allowDuplicates -> 1
            visited.contains(pos) -> 0
            else -> {
                visited.add(pos)
                1
            }
        }
    }
    val neighbors = deltas.map { delta ->
        Pos(pos.x + delta.first, pos.y + delta.second)
    }
    val validNeighbors = positions[nr + 1].filter { nextPos ->
        neighbors.any {
            it.x == nextPos.x && it.y == nextPos.y
        }
    }
    if (validNeighbors.isEmpty()) return 0
    return validNeighbors.sumOf { neighbor ->
        traverse(positions, nr + 1, neighbor, visited, allowDuplicates)
    }
}

fun solve(input: List<String>, allowDuplicates: Boolean): Long {
    val positions = getPositions(input)
    return positions[0].sumOf {
        val sum = traverse(positions, 0, it, mutableListOf(), allowDuplicates)
        sum
    }
}

fun part1(input: List<String>): Long {
    return solve(input, false)
}

fun part2(input: List<String>): Long {
    return solve(input, true)
}

fun main() {
    val day = "10"
    val testInput = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput)
    check(resultPart1 == 36L) { "was $resultPart1" }
    part1(input).println()

    val resultPart2 = part2(testInput)
    check(resultPart2 == 81L) { "was $resultPart2" }
    part2(input).println()
}
