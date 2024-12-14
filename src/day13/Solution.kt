package day13

import println
import readInput
import java.math.BigInteger

data class Machine(val buttonA: Pair<Int, Int>, val buttonB: Pair<Int, Int>, val prize: Pair<Long, Long>)

fun getMachines(input: List<String>, add10000000000000: Boolean): List<Machine> {
    val machines = mutableListOf<Machine>()
    val machineRegex = "(\\d+).+[+|=](\\d+)".toRegex()
    var line = 0
    while (line < input.size) {
        val (_, x1, y1) = machineRegex.find(input[line])?.groupValues!!
        val (_, x2, y2) = machineRegex.find(input[line + 1])?.groupValues!!
        val (_, x3, y3) = machineRegex.find(input[line + 2])?.groupValues!!
        val prize = if (add10000000000000) {
            x3.toLong() + 10000000000000L to y3.toLong() + 10000000000000L
        } else {
            x3.toLong() to y3.toLong()
        }
        machines.add(Machine(x1.toInt() to y1.toInt(), x2.toInt() to y2.toInt(), prize))
        line += 4
    }

    return machines
}

fun Machine.getWin(): Pair<BigInteger, BigInteger>? {
    val a1 = buttonA.first.toBigInteger()
    val a2 = buttonA.second.toBigInteger()
    val b1 = buttonB.first.toBigInteger()
    val b2 = buttonB.second.toBigInteger()
    val p1 = prize.first.toBigInteger()
    val p2 = prize.second.toBigInteger()
    val b = (p2 * a1 - p1 * a2).div (b2 * a1 - b1 * a2)
    val a = (p1 - b * b1).div(a1)
    return if (a1 * a + b1 * b != p1 || a2 * a + b2 * b != p2) null else a to b
}

fun solve(input: List<String>, add10000000000000: Boolean): BigInteger {
    val machines = getMachines(input, add10000000000000)
    val zero = 0.toBigInteger()
    val three = 3.toBigInteger()
    return machines.sumOf { machine ->
        machine.getWin()?.run { first * three + second } ?: zero
    }
}

fun main() {
    val day = "13"
    val testInput = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = solve(testInput, false)
    check(resultPart1 == 480.toBigInteger()) { "was $resultPart1" }
    solve(input, false).println()  // 40369

    val resultPart2 = solve(testInput, true)
    check(resultPart2 == 875318608908.toBigInteger()) { "was $resultPart2" }
    solve(input, true).println()  // 72587986598368
}
