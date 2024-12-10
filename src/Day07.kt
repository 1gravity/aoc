fun main() {
    fun prepareInput(input: List<String>): List<List<Long>> {
        return mutableListOf<List<Long>>().apply {
            input.forEach { line ->
                val numbers = line.split(" ").map { it.trim().trimEnd(':').toLong() }
                add(numbers)
            }
        }
    }

    fun isValid(target: Long, total: Long, numbers: List<Long>): Boolean {
        return if (numbers.size == 1) {
            total + numbers[0] == target || total * numbers[0] == target
        } else {
            val newNumbers = numbers.subList(1, numbers.size)
            isValid(target, total + numbers[0], newNumbers) || isValid(target, total * numbers[0], newNumbers)
        }
    }

    fun concat(n1: Long, n2: Long) = (n1.toString() + n2.toString()).toLong()

    fun isValid2(target: Long, total: Long, numbers: List<Long>): Boolean {
        return if (numbers.size == 1) {
            total + numbers[0] == target || total * numbers[0] == target || concat(total, numbers[0]) == target
        } else {
            val newNumbers = numbers.subList(1, numbers.size)
            isValid2(target, total + numbers[0], newNumbers) || isValid2(target, total * numbers[0], newNumbers) ||
                    isValid2(target, concat(total, numbers[0]), newNumbers)
        }
    }

    fun solve(input: List<String>, isValid: (Long, Long, List<Long>) -> Boolean): Long {
        val data = prepareInput(input)
        var sum = 0L
        data.forEachIndexed { index, line ->
            val target = line[0]
            val total = line[1]
            val numbers = line.subList(2, line.size)
            if (isValid(target, total, numbers)) {
                sum += target
            }
        }
        return sum
    }

    fun part1(input: List<String>): Long {
        return solve(input, ::isValid)
    }

    fun part2(input: List<String>): Long {
        return solve(input, ::isValid2)
    }

    val day = "07"
    val testInput1 = readInput("input/Day${day}_test")
    val testInput2 = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput1)
    check(resultPart1 == 3749L) { "was $resultPart1" }
    part1(input).println()

    val resultPart2 = part2(testInput2)
    check(resultPart2 == 11387L) { "was $resultPart2" }
    part2(input).println()
}
