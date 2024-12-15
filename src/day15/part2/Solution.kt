package day15.part2

import println
import readInput

data class Pos(val x: Int, val y: Int)

sealed class Tile(var pos: Pos) {
    class Wall(pos: Pos) : Tile(pos)
    class Robot(pos: Pos) : Tile(pos)
    class Box(pos: Pos, var other: Box? = null) : Tile(pos)
    class Empty(pos: Pos) : Tile(pos)
}

enum class Direction(val pos: Pos) {
    UP(Pos(0, -1)),
    DOWN(Pos(0, 1)),
    LEFT(Pos(-1, 0)),
    RIGHT(Pos(1, 0))
}

typealias WareHouse = List<MutableList<Tile>>

fun getWarehouse(input: List<String>): WareHouse {
    val size = input.indexOfFirst { it.isEmpty() }
    val wareHouse = ArrayList<MutableList<Tile>>().apply { repeat(size) { add(ArrayList()) } }
    input.forEachIndexed { y, line ->
        if (y >= size) return@forEachIndexed
        var posX = 0
        line.forEachIndexed { _, c ->
            when (c) {
                '#' -> {
                    wareHouse[y].add(Tile.Wall(Pos(posX++, y)))
                    wareHouse[y].add(Tile.Wall(Pos(posX++, y)))
                }
                '@' -> {
                    wareHouse[y].add(Tile.Robot(Pos(posX++, y)))
                    wareHouse[y].add(Tile.Empty(Pos(posX++, y)))
                }
                'O' -> {
                    val box1 = Tile.Box(Pos(posX++, y), null)
                    val box2 = Tile.Box(Pos(posX++, y), box1).also { box1.other = it }
                    wareHouse[y].add(box1)
                    wareHouse[y].add(box2)
                }
                '.' -> {
                    wareHouse[y].add(Tile.Empty(Pos(posX++, y)))
                    wareHouse[y].add(Tile.Empty(Pos(posX++, y)))
                }
                else -> error("Invalid tile $c")
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

fun WareHouse.getBoxes(box: Tile.Box, direction: Direction): Pair<Tile.Box, Tile.Box> {
    val otherBox = box.other!!
    return if (direction == Direction.UP || direction == Direction.DOWN) {
        Pair(box, otherBox)
    } else if (direction == Direction.LEFT) {
        if (otherBox.pos.x < box.pos.x) {
            Pair(otherBox, box)
        } else {
            Pair(box, otherBox)
        }
    } else if (otherBox.pos.x < box.pos.x) {
        Pair(box, otherBox)
    } else {
        Pair(otherBox, box)
    }
}

fun WareHouse.move(tile: Tile, direction: Direction): Boolean {
    val newPos = Pos(tile.pos.x + direction.pos.x, tile.pos.y + direction.pos.y)
    val newTile = this[newPos.y][newPos.x]
    return when (newTile) {
        is Tile.Wall -> false
        is Tile.Empty -> moveTo(tile, newPos)
        is Tile.Box -> {
            val (box1, box2) = getBoxes(newTile, direction)
            if (canBeMoved(box1, direction) && canBeMoved(box2, direction)) {
                move(box1, direction)
                move(box2, direction)
                moveTo(tile, newPos)
            } else {
                false
            }
        }
        else -> false
    }
}

fun WareHouse.canBeMoved(tile: Tile, direction: Direction): Boolean {
    val newPos = Pos(tile.pos.x + direction.pos.x, tile.pos.y + direction.pos.y)
    val newTile = this[newPos.y][newPos.x]
    return when (newTile) {
        is Tile.Wall -> false
        is Tile.Empty -> true
        is Tile.Box -> {
            val (box1, box2) = getBoxes(newTile, direction)
            if (box1 == tile || box2 == tile) {
                true
            } else {
                canBeMoved(box1, direction) && canBeMoved(box2, direction)
            }
        }
        else -> false
    }
}

//fun WareHouse.display() {
//    forEach { row ->
//        row.forEach { tile ->
//            print(when (tile) {
//                is Tile.Wall -> '#'
//                is Tile.Empty -> '.'
//                is Tile.Box -> 'O'
//                is Tile.Robot -> '@'
//            })
//        }
//        println("")
//    }
//}

fun WareHouse.count(): Long {
    return flatten().filter { it is Tile.Box }.sumOf { box ->
        val (box1, _) = getBoxes(box as Tile.Box, Direction.LEFT)
        box1.pos.y * 100L + box1.pos.x
    }.div(2)
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

    val resultPart2 = solve(testInput)
    check(resultPart2 == 9021L) { "was $resultPart2" }
    solve(input).println()  // 1462788
}
