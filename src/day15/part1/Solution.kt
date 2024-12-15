package day15.part1

import println
import readInput

data class Pos(val x: Int, val y: Int)

sealed class Tile(var pos: Pos) {
    class Wall(pos: Pos) : Tile(pos)
    class Robot(pos: Pos) : Tile(pos)
    class Box(pos: Pos) : Tile(pos)
    class Empty(pos: Pos) : Tile(pos)
}

enum class Direction(val pos: Pos) {
    UP(Pos(0, -1)),
    DOWN(Pos(0, 1)),
    LEFT(Pos(-1, 0)),
    RIGHT(Pos(1, 0))
}

typealias WareHouse = Array<Array<Tile>>

fun getWarehouse(input: List<String>): WareHouse {
    val size = input.indexOfFirst { it.isEmpty() }
    val wareHouse = Array(size) { Array<Tile>(input[0].length) { Tile.Empty(Pos(0, 0)) } }
    input.forEachIndexed { y, line ->
        if (y >= size) return@forEachIndexed
        line.forEachIndexed { x, c ->
            val pos = Pos(x, y)
            wareHouse[y][x] = when (c) {
                '#' -> Tile.Wall(pos)
                '@' -> Tile.Robot(pos)
                'O' -> Tile.Box(pos)
                else -> Tile.Empty(pos)
            }
        }
    }
    return wareHouse
}

fun getDirections(input: List<String>): List<Direction> {
    val startIndex = input.indexOfFirst { it.isEmpty() } + 1
    val directions = mutableListOf<Direction>()
    input.drop(startIndex).forEachIndexed { y, line ->
        line.map {
            when (it) {
                '^' -> Direction.UP
                'v' -> Direction.DOWN
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> error("Invalid direction $it")
            }
        }.apply { directions.addAll(this) }
    }
    return directions
}

fun WareHouse.moveTo(tile: Tile, newPos: Pos): Boolean {
    this[newPos.y][newPos.x] = tile
    this[tile.pos.y][tile.pos.x] = Tile.Empty(tile.pos)
    tile.pos = newPos
    return true
}

fun WareHouse.move(tile: Tile, direction: Direction): Boolean {
    val newPos = Pos(tile.pos.x + direction.pos.x, tile.pos.y + direction.pos.y)
    val newTile = this[newPos.y][newPos.x]
    return when (newTile) {
        is Tile.Wall -> false
        is Tile.Empty -> moveTo(tile, newPos)
        is Tile.Box -> if (move(newTile, direction)) moveTo(tile, newPos) else false
        else -> false
    }
}

fun WareHouse.count(): Long {
    return flatten().filter { it is Tile.Box }.sumOf { box ->
        box.pos.y * 100L + box.pos.x
    }
}

fun solve(input: List<String>): Long {
    val wareHouse = getWarehouse(input)
    val directions = getDirections(input)
    val robot = wareHouse.flatten().find { it is Tile.Robot } as Tile.Robot
    directions.forEach { direction ->
        wareHouse.move(robot, direction)
    }
    return wareHouse.count()
}

fun main() {
    val day = "15"
    val testInput = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = solve(testInput)
    check(resultPart1 == 10092L) { "was $resultPart1" }
    solve(input).println()  // 1451928
}
