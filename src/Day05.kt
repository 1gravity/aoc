import java.util.BitSet

fun main() {
    fun part1(input: List<String>): Int {

        val rules = mutableListOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()
        input.forEach { line ->
            if (line.length == 5) {
                val (p1, p2) = line.split("|").map { it.toInt() }
                rules.add(Pair(p1, p2))
            } else if (line.length > 5) {
                updates.add(line.split(",").map { it.toInt() })
            }
        }

        var sum = 0

        updates.forEach outer@ { update ->
            val visited = BitSet(100)
            update.forEach { page ->
                if (rules.any { (p1, p2) ->
                    p2 == page && visited.get(p1).not() && update.contains(p1)
                }) {
                    return@outer
                }
                visited.set(page)
            }
            if (visited.cardinality() == update.size) {
                sum += update[update.size.div(2)]
            }
        }

        return sum
    }

    fun List<Int>.isValidUpdate(rules: List<Pair<Int, Int>>): Boolean {
        val visited = BitSet(100)
        this.forEach { page ->
            if (rules.any { (p1, p2) ->
                    p2 == page && visited.get(p1).not() && this.contains(p1)
                }) {
                return false
            }
            visited.set(page)
        }
        return true
    }

    fun part2(input: List<String>): Int {
        val rules = mutableListOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()
        input.forEach { line ->
            if (line.length == 5) {
                val (p1, p2) = line.split("|").map { it.toInt() }
                rules.add(Pair(p1, p2))
            } else if (line.length > 5) {
                updates.add(line.split(",").map { it.toInt() })
            }
        }

        val incorrectlyOrdered = mutableListOf<List<Int>>()
        updates.forEach outer@ { update ->
            if (update.isValidUpdate(rules).not()) {
                incorrectlyOrdered.add(update)
            }
        }

        val correctlyOrdered = mutableListOf<List<Int>>()
        incorrectlyOrdered.forEach { update ->
            val correct = update.sortedWith(
                object: Comparator<Int> {
                    override fun compare(page1: Int, page2: Int): Int {
                        return when {
                            rules.contains(Pair(page1, page2)) -> -1
                            rules.contains(Pair(page2, page1)) -> 1
                            else -> 0
                        }
                    }
                }
            )
            correctlyOrdered.add(correct)
        }

        return correctlyOrdered.sumOf { update ->
            update[update.size.div(2)]
        }
    }

    val day = "05"
    val testInput1 = readInput("input/Day${day}_test")
    val testInput2 = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    check(part1(testInput1) == 41) { "was ${part1(testInput1)}" }
    part1(input).println()

//    check(part2(testInput2) == 123) { "was ${part2(testInput2)}" }
//    part2(input).println()
}