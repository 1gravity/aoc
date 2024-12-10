import java.util.BitSet

data class Pos(var x: Int, var y: Int)

fun moveUp(pos: Pos, array: Array<Array<Char>>, visited: Array<BitSet>) {
    if (pos.y == 0) return
    if (array[pos.x][pos.y - 1] == '#') {
        moveRight(pos, array, visited)
    } else {
        pos.y--
        visited[pos.x][pos.y] = true
        moveUp(pos, array, visited)
    }
}

fun moveDown(pos: Pos, array: Array<Array<Char>>, visited: Array<BitSet>) {
    if (pos.y == array[0].size - 1) return
    if (array[pos.x][pos.y + 1] == '#') {
        moveLeft(pos, array, visited)
    } else {
        pos.y++
        visited[pos.x][pos.y] = true
        moveDown(pos, array, visited)
    }
}

fun moveLeft(pos: Pos, array: Array<Array<Char>>, visited: Array<BitSet>) {
    if (pos.x == 0) return
    if (array[pos.x - 1][pos.y] == '#') {
        moveUp(pos, array, visited)
    } else {
        pos.x--
        visited[pos.x][pos.y] = true
        moveLeft(pos, array, visited)
    }
}

fun moveRight(pos: Pos, array: Array<Array<Char>>, visited: Array<BitSet>) {
    if (pos.x == array.size - 1) return
    if (array[pos.x + 1][pos.y] == '#') {
        moveDown(pos, array, visited)
    } else {
        pos.x++
        visited[pos.x][pos.y] = true
        moveRight(pos, array, visited)
    }
}

data class InitData(val array: Array<Array<Char>>, val visited: Array<BitSet>, val guard: Pos)

fun main() {
    fun prepareInput(input: List<String>): InitData {
        val array = Array<Array<Char>>(input.size) {
            Array<Char>(input[0].length) { ' ' }
        }

        val visited = Array<BitSet>(input.size) {
            BitSet()
        }

        val guard = Pos(0, 0)
        input.forEachIndexed { y, line -> line.forEachIndexed { x, char ->
            if (char == '^') {
                guard.x = x
                guard.y = y
            }
            array[x][y] = char
        } }

        visited[guard.x][guard.y] = true

        return InitData(array, visited, guard)
    }

    fun part1(input: List<String>): Int {
        val (array, visited, guard) = prepareInput(input)
        moveUp(guard, array, visited)
        return visited.sumOf { it.cardinality() }
    }

    fun part2(input: List<String>): Int {
        val (array, visited, guard) = prepareInput(input)
        var count = 0
        array.forEachIndexed { x, col ->
            col.forEachIndexed { y, char ->
                if (char == '.') {
                    array[x][y] = '#'
                    runCatching {
                        visited.forEach { it.clear() }
                        moveUp(guard.copy(), array, visited)
                    }.onFailure { count++ }
                    array[x][y] = '.'
                }
            }
        }
        return count
    }

    val day = "06"
    val testInput1 = readInput("input/Day${day}_test")
    val testInput2 = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    check(part1(testInput1) == 41) { "was ${part1(testInput1)}" }
    part1(input).println()

    check(part2(testInput2) == 6) { "was ${part2(testInput2)}" }
    part2(input).println()
}
