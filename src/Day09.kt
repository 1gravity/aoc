fun String.expand(): ArrayList<Int> {
    return ArrayList<Int>().apply {
        var isFree = false
        var id = 0
        this@expand.forEach { ch ->
            val nr = ch.code - '0'.code
            if (isFree) {
                repeat(nr) { add(-1) }
            } else {
                repeat(nr) { add(id) }
                id++
            }
            isFree = isFree.not()
        }
    }
}

fun ArrayList<Int>.compact(): ArrayList<Int> {
    val emptyPos = generateSequence(this@compact.indexOf(-1)) {
        subList(it +1, size).indexOf(-1) + it + 1
    }.iterator()

    for (index in size-1 downTo 0) {
        val nr = this@compact[index]
        if (nr != -1) {
            val emptyPosition = emptyPos.next()
            if (emptyPosition == -1 || emptyPosition > index) break
            this@compact[emptyPosition] = nr
            this@compact[index] = -1
        }
    }
    return this
}

fun ArrayList<Int>.next(id: Int, startPos: Int): IntRange? {
    val start = this.subList(startPos, size).indexOf(id) + startPos
    if (start == startPos -1) return null
    val next = this.subList(start, size).indexOfFirst { it != id }
    val end = if (next == -1) size - 1 else next + start - 1
    return IntRange(start, end)
}

fun ArrayList<Int>.find(id: Int): Sequence<IntRange> {
    return generateSequence({next(id, 0)}) {
        next(id, it.endInclusive + 1)
    }
}

fun ArrayList<Int>.compact2(): ArrayList<Int> {
    for (id in max() downTo 0) {
        val block = find(id).first()
        val blockLength = block.endInclusive - block.start
        find(-1).firstOrNull { emptyBlock ->
            emptyBlock.start < block.start && (emptyBlock.endInclusive - emptyBlock.start) >= blockLength
        }?.let { emptyBlock ->
            (0..blockLength).forEach { index ->
                this@compact2[emptyBlock.start + index] = id
                this@compact2[block.start + index] = -1
            }
        }
    }
    return this
}

fun ArrayList<Int>.sum(): Long = foldIndexed(0) { index, acc, nr ->
    if (nr != -1) acc + index * nr else acc
}

fun part1(input: List<String>): Long {
    return input[0].expand().compact().sum()
}

fun part2(input: List<String>): Long {
    return input[0].expand().compact2().sum()
}

fun main() {
    val day = "09"
    val testInput1 = readInput("input/Day${day}_test")
    val testInput2 = readInput("input/Day${day}_test")
    val input = readInput("input/Day${day}")

    val resultPart1 = part1(testInput1)
    check(resultPart1 == 1928L) { "was $resultPart1" }
    part1(input).println()  // 6241633730082

    val resultPart2 = part2(testInput2)
    check(resultPart2 == 2858L) { "was $resultPart2" }
    part2(input).println()  // 6265268809555
}
