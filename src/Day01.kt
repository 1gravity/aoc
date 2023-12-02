val regexNumbers = "(1)|(2)|(3)|(4)|(5)|(6)|(7)|(8)|(9)|(?=(one))|(?=(two))|(?=(three))|(?=(four))|(?=(five))|(?=(six))|(?=(seven))|(?=(eight))|(?=(nine))".toRegex()

val numbers = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9
)

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            if (line.isNotBlank()) {
                val matches = regexNumbers.findAll(line)
                val first = matches.first().groupValues.first { it.isNotBlank() }
                val last = matches.last().groupValues.first { it.isNotBlank() }
                "${numbers[first]}${numbers[last]}".toInt()
            } else {
                error("EMPTY LINE")
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("input/Day01")
    part1(input).println()
    part2(input).println()
}
