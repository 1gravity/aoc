fun main() {
    fun part1(input: List<String>): Int {
        val regex = "mul\\((\\d+),(\\d+)\\)".toRegex()
        return input.sumOf { line ->
            regex.findAll(line).sumOf { result ->
                result.groupValues[1].toInt() * result.groupValues[2].toInt()
            }
        }
    }

    fun part2(input: List<String>): Int {
        fun Regex.getAll(line: String): List<Int> = findAll(line).map { result ->
            result.groups[1]?.range?.first ?: -1
        }.toList()
        fun List<Int>.getHighest(pos: Int) = filter { it < pos }.maxOrNull() ?: -1

        val regex = "mul\\((\\d+),(\\d+)\\)".toRegex()
        val dos = "(do\\(\\))".toRegex()
        val donts = "(don't\\(\\))".toRegex()
        return input.sumOf { line ->
            val doList = dos.getAll(line)
            val dontList = donts.getAll(line)
            regex.findAll(line).sumOf { result ->
                val start = result.groups[1]?.range?.first ?: 0
                val doHighest = doList.getHighest(start)
                val dontHighest = dontList.getHighest(start)
                if (doHighest > dontHighest || dontHighest == -1) {
                    result.groupValues[1].toInt() * result.groupValues[2].toInt()
                } else {
                    0
                }
            }
        }
    }

    val day = "03"
    val input = readInput("input/Day${day}")
    val testInput1 = readInput("input/Day${day}_test_part1")
    val testInput2 = readInput("input/Day${day}_test_part2")

    check(part1(testInput1) == 161) { "was ${part1(testInput1)}" }
    part1(input).println()
    check(part2(testInput2) == 48) { "was ${part2(testInput2)}" }
    part2(input).println()
}
