fun main() {
    val winningNrRegex = ".+: (.+ )\\|".toRegex()
    val scratchNrRegex = "\\| (.+)".toRegex()

    fun String.winningNrs(): Set<Int> {
        return winningNrRegex.find(this)?.groupValues?.get(1)
            ?.chunked(3)
            ?.map { it.trim().toInt() }
            ?.toHashSet() ?: emptySet()
    }

    fun String.scratchNrs(): List<Int> {
        return scratchNrRegex.find(this)?.groupValues?.get(1)
            ?.chunked(3)
            ?.map { it.trim().toInt() } ?: emptyList()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val winningNrs = line.winningNrs()
            val scratchNrs = line.scratchNrs()
            scratchNrs.fold(0) { acc, nr ->
                (if (winningNrs.contains(nr)) {
                    if (acc == 0) 1 else acc * 2
                } else acc).toInt()
            }.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        fun String.nrOfWins(): Int {
            val winningNrs = winningNrs()
            return scratchNrs().sumOf { nr ->
                if (winningNrs.contains(nr)) 1 else 0.toInt()
            }
        }

        val map = mutableMapOf<Int, Int>()
        input.forEachIndexed { index, line ->
            map.putIfAbsent(index, 1)
            val nrOfWins = line.nrOfWins()
            val maxIndex = (index + nrOfWins).coerceAtMost(input.size - 1)
            for (i in index+1 ..maxIndex) {
                map.putIfAbsent(i, 1)
                map[i] = map[i]!! + map[index]!!
            }
        }
        return map.values.sum()
    }

    val day = "04"
    val testInput1 = readInput("input/Day${day}_test_part1")
    check(part1(testInput1) == 13) { "was ${part1(testInput1)}" }

    val testInput2 = readInput("input/Day${day}_test_part2")
    check(part2(testInput1) == 30) { "was ${part2(testInput2)}" }

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()  // 14624680
}
