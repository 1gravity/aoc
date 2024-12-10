fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        val coordinates = HashMap<Char, MutableList<Pair<Int, Int>>>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                coordinates.putIfAbsent(c, mutableListOf())
                coordinates[c]?.add(Pair(x, y))
            }
        }
        val allX = coordinates['X']
        val allM = coordinates['M']
        val allA = coordinates['A']
        val allS = coordinates['S']
        allX?.forEach { (x, y) ->
            if (allM?.any { (x2, y2) -> x2 == x && y2 == y + 1 } == true &&
                allA?.any { (x2, y2) -> x2 == x && y2 == y + 2 } == true &&
                allS?.any { (x2, y2) -> x2 == x && y2 == y + 3 } == true) { sum++ }
            if (allM?.any { (x2, y2) -> x2 == x && y2 == y - 1 } == true &&
                allA?.any { (x2, y2) -> x2 == x && y2 == y - 2 } == true &&
                allS?.any { (x2, y2) -> x2 == x && y2 == y - 3 } == true) { sum++ }
            if (allM?.any { (x2, y2) -> x2 == x + 1 && y2 == y } == true &&
                allA?.any { (x2, y2) -> x2 == x + 2 && y2 == y } == true &&
                allS?.any { (x2, y2) -> x2 == x + 3 && y2 == y } == true) { sum++ }
            if (allM?.any { (x2, y2) -> x2 == x - 1 && y2 == y } == true &&
                allA?.any { (x2, y2) -> x2 == x - 2 && y2 == y } == true &&
                allS?.any { (x2, y2) -> x2 == x - 3 && y2 == y } == true) { sum++ }

            if (allM?.any { (x2, y2) -> x2 == x + 1 && y2 == y + 1 } == true &&
                allA?.any { (x2, y2) -> x2 == x + 2 && y2 == y + 2 } == true &&
                allS?.any { (x2, y2) -> x2 == x + 3 && y2 == y + 3 } == true) { sum++ }
            if (allM?.any { (x2, y2) -> x2 == x + 1 && y2 == y - 1 } == true &&
                allA?.any { (x2, y2) -> x2 == x + 2 && y2 == y - 2 } == true &&
                allS?.any { (x2, y2) -> x2 == x + 3 && y2 == y - 3 } == true) { sum++ }
            if (allM?.any { (x2, y2) -> x2 == x - 1 && y2 == y + 1 } == true &&
                allA?.any { (x2, y2) -> x2 == x - 2 && y2 == y + 2 } == true &&
                allS?.any { (x2, y2) -> x2 == x - 3 && y2 == y + 3 } == true) { sum++ }
            if (allM?.any { (x2, y2) -> x2 == x - 1 && y2 == y - 1 } == true &&
                allA?.any { (x2, y2) -> x2 == x - 2 && y2 == y - 2 } == true &&
                allS?.any { (x2, y2) -> x2 == x - 3 && y2 == y - 3 } == true) { sum++ }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val coordinates = HashMap<Char, MutableList<Pair<Int, Int>>>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                coordinates.putIfAbsent(c, mutableListOf())
                coordinates[c]?.add(Pair(x, y))
            }
        }
        val allM = coordinates['M']!!
        val allA = coordinates['A']!!
        val allS = coordinates['S']!!
        allM.forEach { (x, y) ->
            /*
                M.S     S.S     M.M     S.M
                .A.     .A.     .A.     .A.
                M.S     M.M     S.S     S.M
             */
            if (allM.any { (x2, y2) -> x2 == x && y2 == y + 2 } == true &&
                allA.any { (x2, y2) -> x2 == x + 1 && y2 == y + 1 } == true &&
                allS.any { (x2, y2) -> x2 == x + 2 && y2 == y } == true &&
                allS.any { (x2, y2) -> x2 == x + 2 && y2 == y + 2 } == true
                ) { sum++ }
            if (allM.any { (x2, y2) -> x2 == x + 2 && y2 == y } == true &&
                allA.any { (x2, y2) -> x2 == x + 1 && y2 == y - 1 } == true &&
                allS.any { (x2, y2) -> x2 == x && y2 == y - 2 } == true &&
                allS.any { (x2, y2) -> x2 == x + 2 && y2 == y - 2 } == true
                ) { sum++ }
            if (allM.any { (x2, y2) -> x2 == x + 2 && y2 == y } == true &&
                allA.any { (x2, y2) -> x2 == x + 1 && y2 == y + 1 } == true &&
                allS.any { (x2, y2) -> x2 == x && y2 == y + 2 } == true &&
                allS.any { (x2, y2) -> x2 == x + 2 && y2 == y + 2 } == true
            ) { sum++ }
            if (allM.any { (x2, y2) -> x2 == x && y2 == y + 2 } == true &&
                allA.any { (x2, y2) -> x2 == x - 1 && y2 == y + 1 } == true &&
                allS.any { (x2, y2) -> x2 == x - 2 && y2 == y } == true &&
                allS.any { (x2, y2) -> x2 == x - 2 && y2 == y + 2 } == true
            ) { sum++ }
        }

        return sum
    }

    val day = "04"
    val testInput1 = readInput("input/Day${day}_test_part1")
    val testInput2 = readInput("input/Day${day}_test_part2")
    val input = readInput("input/Day${day}")

    check(part1(testInput1) == 18) { "was ${part1(testInput1)}" }
    part1(input).println()

    check(part2(testInput2) == 9) { "was ${part2(testInput2)}" }
    part2(input).println()
}
