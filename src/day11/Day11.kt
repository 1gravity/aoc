package day11

import println
import readInput

typealias Result = MutableMap<Long, Pair<Long, Long?>>

fun processNr(result: Result, nr: Long) {
    when {
        nr == 0L -> result.putIfAbsent(0L, 1L to null)
        nr.toString().length % 2 == 0 -> {
            val s = nr.toString()
            val len = s.length
            result[nr] = s.substring(0, len / 2).toLong() to s.substring(len / 2).toLong()
        }
        else -> result.putIfAbsent(nr, nr * 2024 to null)
    }
}

fun Result.expand(): Result {
    val result = mutableMapOf<Long, Pair<Long, Long?>>().apply { putAll(this@expand) }
    forEach { (_, pair) ->
        processNr(result, pair.first)
        pair.second?.let { second -> processNr(result, second) }
    }
    return result
}

val memoized = mutableMapOf<Pair<Long, Int>, Long>()

fun Result.count(nr: Long, nrOfIteration: Int): Long {
    memoized[nr to nrOfIteration]?.let { return it }
    if (nrOfIteration == 0) return 1L
    val (first, second) = this[nr] ?: return 1L
    return (count(first, nrOfIteration - 1) + (second?.let { count(it, nrOfIteration - 1) } ?: 0))
        .also { memoized[nr to nrOfIteration] = it }
}

fun solve(input: List<String>, nrOfIterations: Int): Long {
    val startStones = input[0].split(" ").map { it.toLong() }

    var result = mutableMapOf<Long, Pair<Long, Long?>>()
    startStones.forEach { processNr(result, it) }
    repeat(nrOfIterations - 1) {
        result = result.expand()
    }

    return startStones.sumOf {
        result.count(it, nrOfIterations)
    }
}

fun main() {
    val day = "11"
    val testInput = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = solve(testInput, 25)
    check(resultPart1 == 55312L) { "was $resultPart1" }
    solve(input, 25).println()  // 189092

    solve(input, 75).println()  // 224869647102559
}
