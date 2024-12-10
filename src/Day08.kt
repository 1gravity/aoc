fun main() {
    data class Pos(val x: Int, val y: Int)
    fun prepareInput(input: List<String>): Map<Char, List<Pos>> {
        return mutableMapOf<Char, List<Pos>>().apply {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, ch ->
                    if (ch.isLetter() || ch.isDigit()) {
                        val list = this.getOrPut(ch) { mutableListOf() }.toMutableList()
                        list.add(Pos(x, y))
                        put(ch, list)
                    }
                }
            }
        }
    }

    fun isValidAntiNode(pos: Pos, antiNodes: Array<Array<Boolean>>): Boolean {
        return pos.x >= 0 && pos.y >= 0 && pos.y < antiNodes.size && pos.x < antiNodes[0].size
    }

    fun processSide1(p1: Pos, p2: Pos, antiNodes: Array<Array<Boolean>>, recursive: Boolean) {
        val deltaX = p1.x - p2.x
        val deltaY = p1.y - p2.y
        val antiNode = Pos(p1.x + deltaX, p1.y + deltaY)
        if (isValidAntiNode(antiNode, antiNodes)) {
            antiNodes[antiNode.y][antiNode.x] = true
            if (recursive) {
                antiNodes[p1.y][p1.x] = true
                antiNodes[p2.y][p2.x] = true
                processSide1(antiNode, p1, antiNodes, true)
            }
        }
    }

    fun processSide2(p1: Pos, p2: Pos, antiNodes: Array<Array<Boolean>>, recursive: Boolean) {
        val deltaX = p1.x - p2.x
        val deltaY = p1.y - p2.y
        val antiNode = Pos(p2.x - deltaX, p2.y - deltaY)
        if (isValidAntiNode(antiNode, antiNodes)) {
            antiNodes[antiNode.y][antiNode.x] = true
            if (recursive) {
                antiNodes[p1.y][p1.x] = true
                antiNodes[p2.y][p2.x] = true
                processSide2(p2, antiNode, antiNodes, true)
            }
        }
    }

    fun process(positions: List<Pos>, antiNodes: Array<Array<Boolean>>, recursive: Boolean) {
        positions.forEachIndexed { index, pos ->
            positions.subList(index + 1, positions.size).forEach { nextPos ->
                processSide1(pos, nextPos, antiNodes, recursive)
                processSide2(pos, nextPos, antiNodes, recursive)
            }
        }
    }

    fun Array<Array<Boolean>>.print() {
        forEach { col ->
            col.forEach { if (it) print("#") else print(".") }
            println("")
        }
    }

    fun solve(input: List<String>, recursive: Boolean): Int {
        val data: Map<Char, List<Pos>> = prepareInput(input)
        val antiNodes = Array<Array<Boolean>>(input.size) { Array<Boolean>(input[0].length) { false } }
        data.forEach { char, positions ->
            process(positions, antiNodes, recursive)
        }
        antiNodes.print()
        return antiNodes.sumOf { it.count { it } }
    }

    fun part1(input: List<String>): Int {
        return solve(input, false)
    }

    fun part2(input: List<String>): Int {
        return solve(input, true)
    }

    val day = "08"
    val testInput1 = readInput("input/Day${day}_test")
    val testInput2 = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput1)
    check(resultPart1 == 14) { "was $resultPart1" }
    part1(input).println()

    val resultPart2 = part2(testInput2)
    check(resultPart2 == 34) { "was $resultPart2" }
    // 941 is too high
    // 935 nope
    // 934 yes
    // 931 is too low
    part2(input).println()
}
