fun main() {
    fun Regex.getNr(input: String) = find(input)?.groupValues?.get(1)?.toInt() ?: 0
    val regexGame = "Game (\\d+):".toRegex()
    val regexRed = "(\\d+) red".toRegex()
    val regexGreen = "(\\d+) green".toRegex()
    val regexBlue = "(\\d+) blue".toRegex()

    fun part1(input: List<String>): Int {
        val maxCubes = arrayOf(12, 13, 14)
        return input.sumOf { line ->
            val gameNr = regexGame.getNr(line)
            val chunks = line.split(";")
            val redFail = chunks.any { regexRed.getNr(it) > maxCubes[0] }
            val greenFail = chunks.any { regexGreen.getNr(it) > maxCubes[1] }
            val blueFail = chunks.any { regexBlue.getNr(it) > maxCubes[2] }
            if (redFail || greenFail || blueFail) 0 else gameNr
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("input/Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("input/Day02")
    part1(input).println()
    part2(input).println()
}
