fun main() {
    val adjacent = arrayOf(
        Pair(-1, -1), Pair(0, -1), Pair(1, -1),
        Pair(-1, 0), Pair(1, 0),
        Pair(-1, 1), Pair(0, 1), Pair(1, 1)
    )

    val regexNumber = "(\\d+)".toRegex()

    fun part1(input: List<String>): Int {
        fun isSymbol(x: Int, y: Int) = input[y][x].isDigit().not() && input[y][x] != '.'

        fun isPart(x: Int, y: Int): Boolean {
            return adjacent.any { (dx, dy) ->
                val nx = x + dx
                val ny = y + dy
                nx >= 0 && nx < input[y].length && ny >= 0 && ny < input.size && isSymbol(nx, ny)
            }
        }

        var sum = 0
        input.forEachIndexed { y, line ->
            regexNumber.findAll(line).forEach { matchResult ->
                val number = matchResult.groupValues[1].toInt()
                val hasSymbol = matchResult.range.any { x ->
                    isPart(x, y)
                }
                if (hasSymbol) sum += number
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        fun isSymbol(x: Int, y: Int) = input[y][x] == '*'

        val gears = Array(input.size) {
            Array(input[0].length) {
                ArrayList<Int>()
            }
        }

        input.forEachIndexed { y, line ->
            regexNumber.findAll(line).forEach { matchResult ->
                val number = matchResult.groupValues[1].toInt()
                val gearsFound = HashMap<String, Int>()
                matchResult.range.forEach { x ->
                    adjacent.forEach { (dx, dy) ->
                        val nx = x + dx
                        val ny = y + dy
                        if (nx >= 0 && nx < input[y].length && ny >= 0 && ny < input.size && isSymbol(nx, ny)) {
                            if (gearsFound["$nx$ny"] != number) {
                                gearsFound["$nx$ny"] = number
                                gears[ny][nx].add(number)
                            }
                        }
                    }
                }
            }
        }

        var sum = 0
        for (y in input.indices) {
            for (x in 0 until input[0].length) {
                if (gears[y][x].size == 2) {
                    sum += gears[y][x][0] * gears[y][x][1]
                }
            }
        }
        return sum
    }

    val day = "03"
    val testInput1 = readInput("input/Day${day}_test_part1")
    check(part1(testInput1) == 4361) { "was ${part1(testInput1)}" }

    val testInput2 = readInput("input/Day${day}_test_part2")
    check(part2(testInput1) == 467835) { "was ${part2(testInput2)}" }

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
