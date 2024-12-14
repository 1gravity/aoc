package day14

import println
import readInput
import kotlin.collections.forEach
import kotlin.math.max

data class Pos(var x: Int, var y: Int)
data class Robot(val pos: Pos, val velocityX: Int, val velocityY: Int)

fun getRobots(input: List<String>): List<Robot> {
    val regex = "p=(.+),(.+) v=(.+),(.+)".toRegex()
    return input.map { line ->
        val (_, x, y, velocityX, velocityY) = regex.find(line)!!.groupValues
        Robot(Pos(x.toInt(), y.toInt()), velocityX.toInt(), velocityY.toInt())
    }
}

fun Robot.move(width: Int, height: Int) {
    pos.x = if (pos.x + velocityX < 0) (width + pos.x + velocityX) % width else (pos.x + velocityX) % width
    pos.y = if (pos.y + velocityY < 0) (height + pos.y + velocityY) % height else (pos.y + velocityY) % height
}

fun List<Robot>.print(width: Int, height: Int) {
    val grid = Array(height) { Array(width) { 0 } }
    forEach { grid[it.pos.y][it.pos.x]++ }
    grid.forEach { println(it.joinToString("") { if (it == 0) " " else "*" }) }
}

fun part1(input: List<String>, width: Int, height: Int): Long {
    val robots = getRobots(input)
    repeat(100) { second ->
        robots.forEach { robot -> robot.move(width, height) }
    }

    val midX = (width - 1) / 2
    val midY = (height - 1) / 2

    val quadrant1 = robots.count { it.pos.x < midX && it.pos.y < midY }.toLong()
    val quadrant2 = robots.count { it.pos.x > midX && it.pos.y < midY }.toLong()
    val quadrant3 = robots.count { it.pos.x < midX && it.pos.y > midY }.toLong()
    val quadrant4 = robots.count { it.pos.x > midX && it.pos.y > midY }.toLong()

    return quadrant1 * quadrant2 * quadrant3 * quadrant4
}

fun getLongestSequence(robots: List<Robot>, width: Int, line: Int): Int {
    val grid = Array(width) { false }
    val robots = robots.filter { it.pos.y == line }
    robots.forEach { grid[it.pos.x] = true }

    var maxLength = 0
    var currentLength = 0
    grid.reduce { prev, current ->
        if (prev == current && current) {
            currentLength++
            maxLength = max(maxLength, currentLength)
        } else {
            currentLength = 1
        }
        current
    }

    return maxLength
}

fun part2(input: List<String>, width: Int, height: Int): Int {
    val robots = getRobots(input)
    val trees = mutableListOf<Int>()
    repeat(100000) { second ->
        robots.forEach { robot -> robot.move(width, height) }
        (0..height).windowed(2).forEach { (y1, y2) ->
            val longest1 = getLongestSequence(robots, width, y1)
            val longest2 = getLongestSequence(robots, width, y2)
            if (longest1 > 15 && longest2 > 15) {
                trees.add(second + 1)
                println("Second: ${second + 1}")
                robots.print(width, height)
                return@repeat
            }
        }
    }
    return trees.first()
}

fun main() {
    val day = "14"
    val testInput = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput, 11, 7)
    check(resultPart1 == 12L) { "was $resultPart1" }
    part1(input, 101, 103).println()    // 230900224

    part2(input, 101, 103).println()    // 6532
}
