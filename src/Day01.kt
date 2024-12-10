import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        input.forEach { line ->
            val (left, right) = line.split("   ")
            leftList.add(left.trim().toInt())
            rightList.add(right.trim().toInt())
        }
        leftList.sort()
        rightList.sort()
        return leftList.zip(rightList).sumOf { (left, right) -> (left - right).absoluteValue }
    }

    fun part2(input: List<String>): Int {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        input.forEach { line ->
            val (left, right) = line.split("   ")
            leftList.add(left.trim().toInt())
            rightList.add(right.trim().toInt())
        }

        return leftList.sumOf { left ->
            rightList.count { right -> left == right }.times(left)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day01_test")
    part1(testInput).println()
    check(part1(testInput) == 11)
    part2(testInput).println()
    check(part2(testInput) == 31)

    val input = readInput("input/Day01")
    part1(input).println()
    part2(input).println()
}
