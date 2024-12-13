package day12

import println
import readInput

data class Pos(val x: Int, val y: Int)
data class Plant(val c: Char, val pos: Pos)

val deltas = listOf(0 to -1, 0 to 1, 1 to 0, -1 to 0)

fun getNeighbors(input: List<String>): Map<Plant, MutableList<Plant>> {
    val plants = HashMap<Plant, MutableList<Plant>>()
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            val key = Plant(c, Pos(x, y))
            plants[key] = mutableListOf<Plant>()
        }
    }
    plants.forEach { (element, neighbors) ->
        val (c, pos) = element
        deltas.forEach { delta ->
            val neighborKey = Plant(c, Pos(pos.x + delta.first, pos.y + delta.second))
            if (plants.contains(neighborKey)) {
                neighbors.add(Plant(c, neighborKey.pos))
            }
        }
    }
    return plants
}

fun Map<Plant, MutableList<Plant>>.getRegion(
    plant: Plant,
    visited: MutableSet<Plant>
): List<Plant>  {
    if (visited.contains(plant)) return emptyList()
    visited.add(plant)

    val region = mutableListOf(plant)
    this[plant]?.forEach { neighbor ->
        region += getRegion(neighbor, visited)
    }
    return region
}

fun Map<Plant, MutableList<Plant>>.getRegions(): List<List<Plant>> {
    val visited = mutableSetOf<Plant>()
    val regions = ArrayList<List<Plant>>()
    this.forEach { (plant, _) ->
        val region = getRegion(plant, visited)
        if (region.isNotEmpty()) regions.add(region)
    }
    return regions
}

fun part1(input: List<String>): Long {
    val plants = getNeighbors(input)
    val regions = plants.getRegions()
    return regions.sumOf { region ->
        val perimeter = region.sumOf { plant -> 4L - (plants[plant]?.size ?: 0) }
        region.size * perimeter
    }
}

fun countEdges(edges: Array<Boolean>): Long {
    var count = if (edges[0]) 1L else 0L
    edges.toList().windowed(2, 1) { (a, b) ->
        if (a != b && b) count++
    }
    return count
}

fun List<Plant>.countCornerCases(): Int {
    var count = 0
    forEach { plant ->
        if (
            any { neighbor -> neighbor.pos.x == plant.pos.x + 1 && neighbor.pos.y == plant.pos.y + 1} &&
            none { neighbor -> neighbor.pos.x == plant.pos.x && neighbor.pos.y == plant.pos.y + 1} &&
            none { neighbor -> neighbor.pos.x == plant.pos.x + 1 && neighbor.pos.y == plant.pos.y}
        ) {
            count++
        }
        if (
            any { neighbor -> neighbor.pos.x == plant.pos.x - 1 && neighbor.pos.y == plant.pos.y + 1} &&
            none { neighbor -> neighbor.pos.x == plant.pos.x - 1 && neighbor.pos.y == plant.pos.y} &&
            none { neighbor -> neighbor.pos.x == plant.pos.x && neighbor.pos.y == plant.pos.y + 1}
        ) {
            count++
        }
    }
    return count
}

fun part2(input: List<String>): Long {
    val plants = getNeighbors(input)
    val regions = plants.getRegions()

    return regions.sumOf { region ->
        val horizontalEdges = Array(input.size + 1) { Array(input[0].length) { false } }
        val verticalEdges = Array(input[0].length + 1) { Array(input.size) { false } }
        region.forEach { plant ->
            if (region.none { neighbor -> neighbor.pos.x == plant.pos.x - 1 && neighbor.pos.y == plant.pos.y }) {
                verticalEdges[plant.pos.x][plant.pos.y] = true  // left
            }
            if (region.none { neighbor -> neighbor.pos.x == plant.pos.x + 1 && neighbor.pos.y == plant.pos.y }) {
                verticalEdges[plant.pos.x + 1][plant.pos.y] = true  // right
            }
            if (region.none { neighbor -> neighbor.pos.x == plant.pos.x && neighbor.pos.y == plant.pos.y - 1 }) {
                horizontalEdges[plant.pos.y][plant.pos.x] = true  // top
            }
            if (region.none { neighbor -> neighbor.pos.x == plant.pos.x && neighbor.pos.y == plant.pos.y + 1 }) {
                horizontalEdges[plant.pos.y + 1][plant.pos.x] = true  // bottom
            }
        }
        val cornerCases = region.countCornerCases()
        val sides = cornerCases.times(2) + verticalEdges.sumOf(::countEdges) + horizontalEdges.sumOf(::countEdges)

        region.size * sides
    }
}

fun main() {
    val day = "12"
    val testInput = readInput("input/Day${day}_test")
    val testInput2 = readInput("input/Day${day}_test2")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput)
    check(resultPart1 == 1930L) { "was $resultPart1" }
    part1(input).println()  // 1477924

    val resultPart2 = part2(testInput)
    check(resultPart2 == 1206L) { "was $resultPart2" }

    val resultPart3 = part2(testInput2)
    check(resultPart3 == 10*7+4+4L) { "was $resultPart3" }

    part2(input).println()  // 841934
}
