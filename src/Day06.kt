fun main() {
    val timeRegex = "Time:\\s+(.+)$".toRegex()
    val distanceRegex = "Distance:\\s+(.+)$".toRegex()

    fun part1(input: List<String>): Int {
        fun String.readNumbers(regex: Regex) = regex.find(this)
            ?.groupValues
            ?.get(1)
            ?.split(" ")
            ?.filter { it.isNotBlank() }
            ?.map { it.trim().toInt() }

        val times = input[0].readNumbers(timeRegex)
        val distances = input[1].readNumbers(distanceRegex)

        var result = 1

        times?.forEachIndexed { index, time ->
            val distance = distances?.get(index) ?: 0
            var sum = 0
            for (i in 1..<time) {
                val nrOfMoves = time - i
                val moveDistance = (time - nrOfMoves) * nrOfMoves
                if (moveDistance > distance) {
                    sum++
                }
            }
            result *= sum
        }

        return result
    }

    fun part2(input: List<String>): Int {
        fun String.readNumbers(regex: Regex) = regex.find(this)
            ?.groupValues
            ?.get(1)
            ?.replace(" ", "")
            ?.toLong()

        val time = input[0].readNumbers(timeRegex)!!
        val distance = input[1].readNumbers(distanceRegex)!!

        var sum = 0

        for (i in 1L..<time) {
            val nrOfMoves = time - i
            val moveDistance = (time - nrOfMoves) * nrOfMoves
            if (moveDistance > distance) {
                sum++
            }
        }

        return sum
    }

    val day = "06"
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 288) { "was ${part1(testInput)}" }
    check(part2(testInput) == 71503) { "was ${part2(testInput)}" }

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
