fun main() {
    fun isSave(levels: List<Int>): Boolean {
        var safe = true
        var increasing = levels[0] < levels[1]
        levels.reduce { last, current ->
            if (increasing) {
                if (current <= last || current > last + 3) {
                    safe = false
                }
            } else if (current >= last || current < last - 3) {
                safe = false
            }
            current
        }
        return safe
    }

    fun part1(input: List<String>): Int {
        val reports = input.map { it.split(" ").map { it.toInt() } }
        return reports.sumOf { levels ->
            if (isSave(levels)) 1 else 0.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val reports = input.map { it.split(" ").map { it.toInt() } }
        return reports.sumOf { levels ->
            val isSave = isSave(levels) || levels.indices.any { index ->
                val clone = levels.toMutableList()
                clone.removeAt(index)
                isSave(clone)
            }
            if (isSave) 1 else 0.toInt()
        }
    }

    val testInput1 = readInput("input/Day02_test_part1")
    check(part1(testInput1) == 2) { "was ${part1(testInput1)}" }

    val testInput2 = readInput("input/Day02_test_part2")
    check(part2(testInput1) == 4) { "was ${part2(testInput2)}" }

    val input = readInput("input/Day02")
    part1(input).println()
    part2(input).println()
}
