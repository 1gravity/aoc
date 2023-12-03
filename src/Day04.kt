fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val day = "04"
    val testInput1 = readInput("input/Day${day}_test_part1")
    check(part1(testInput1) == 0) { "was ${part1(testInput1)}" }

    val testInput2 = readInput("input/Day${day}_test_part2")
    check(part2(testInput1) == 0) { "was ${part2(testInput2)}" }

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
