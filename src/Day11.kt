import kotlin.math.absoluteValue

fun main() {
    data class Point(var x: Long, var y: Long)

    fun part1(input: List<String>): Long {
        var nr = 1
        val expandedVertically = input.fold(ArrayList<List<Int>>()) { matrix, line ->
            val list = line.map { if (it == '#') nr++ else 0 }
            if (list.any { it > 0 }) {
                matrix.add(list)
            } else {
                matrix.add(list)
                matrix.add(list)
            }
            matrix
        }

        val expandedHorizontally = ArrayList<MutableList<Int>>()
        expandedVertically.forEach { expandedHorizontally.add(ArrayList(it)) }
        for (x in expandedVertically.first().size - 1 downTo 0) {
            val galaxyFound = expandedVertically.any { it[x] > 0 }
            if (galaxyFound.not()) {
                expandedHorizontally.forEach { it.add(x, 0) }
            }
        }

        val galaxies = ArrayList<Point>()
        expandedHorizontally.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c > 0) {
                    galaxies.add(Point(x.toLong(), y.toLong()))
                }
            }
        }

        val permutations = galaxies.foldIndexed(ArrayList<List<Point>>()) { index, permutations, galaxy ->
            galaxies.subList(index + 1, galaxies.size).forEach { otherGalaxy ->
                permutations.add(listOf(galaxy, otherGalaxy))
            }
            permutations
        }

        return permutations.sumOf { pair ->
            val (x1, y1) = pair[0]
            val (x2, y2) = pair[1]
            val dx = x2 - x1
            val dy = y2 - y1
            dx.absoluteValue + dy.absoluteValue
        }
    }

    fun part2(input: List<String>, multiplier: Int): Long {
        var y = 0
        val galaxies = input.fold(ArrayList<Point>()) { galaxies, line ->
            if (line.any { it == '#' }) {
                line.forEachIndexed { x, c ->
                    if (c == '#') {
                        galaxies.add(Point(x.toLong(), y.toLong()))
                    }
                }
                y++
            } else {
                y += multiplier
            }
            galaxies
        }

        for (xPos in input.first().length - 1 downTo 0) {
            val galaxyFound = input.any { it[xPos] == '#' }
            if (galaxyFound.not()) {
                galaxies
                    .filter { it.x > xPos }
                    .forEach {
                        it.x += multiplier - 1
                    }
            }
        }

        val permutations = galaxies.foldIndexed(ArrayList<List<Point>>()) { index, permutations, galaxy ->
            galaxies.subList(index + 1, galaxies.size).forEach { otherGalaxy ->
                permutations.add(listOf(galaxy, otherGalaxy))
            }
            permutations
        }

        return permutations.sumOf { pair ->
            val (x1, y1) = pair[0]
            val (x2, y2) = pair[1]
            val dx = x2 - x1
            val dy = y2 - y1
            dx.absoluteValue + dy.absoluteValue
        }
    }

    val day = "11"
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 374L) { "was ${part1(testInput)}" }
    check(part2(testInput, 10) == 1030L) { "was ${part2(testInput, 10)}" }
    check(part2(testInput, 100) == 8410L) { "was ${part2(testInput, 10)}" }

    val input = readInput("input/Day${day}")
    part1(input).println()  // 10173804
    part2(input, 1000000).println()
}
