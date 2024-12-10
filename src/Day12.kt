fun main() {
    data class Point(var x: Long, var y: Long)

    // .??..??...?##. 1,1,3
    // ^[.|?]*?([?|#])[.|?]+?([#|?])[.|?]+?([#|?]{3})[.|?]*?$
    // ?#?#?#?#?#?#?#? 1,3,1,6
    // ^[.|?]*?([?|#])[.|?]+?([#|?]{3})[.|?]+?([?|#])[.|?]+?([#|?]{6})[.|?]*?$
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val parts = line.split(" ")
            var record = parts[0].trim()
            val groups = parts[1].trim()
                .split(",")
                .map { it.toInt() }
                .toMutableList()

            // eliminate groups with #
            val s = StringBuilder("^.*?")
            groups.forEachIndexed { index, damagedSprings ->
                s.append("([#|?]{$damagedSprings})")
                if (index < groups.size - 1) {
                    s.append("[.|?]+?")
                }
            }
            s.append(".*?\$")
            val regex = s.toString().toRegex()
            val matchResult = regex.find(record)
            matchResult?.groups?.forEachIndexed { index, matchGroup ->
                if (index > 0 && matchGroup?.value?.contains("#") == true) {
                    val tmp = "................................."
                    val start = matchGroup.range.first.minus(1).coerceAtLeast(0)
                    val end = matchGroup.range.last.plus(1).coerceAtMost(record.length - 1)
                    record = record.replaceRange(start, end + 1, tmp.substring(0, end - start + 1))
                    groups[index - 1] = 0
                }
            }

            fun String.nrOfArrangements(groupIndex: Int): Int {
                val s = StringBuilder("^.*?")
                groups.forEachIndexed { index, damagedSprings ->
                    s.append("([#|?]{$damagedSprings})")
                    if (index < groups.size - 1) {
                        s.append("[.|?]+?")
                    }
                }
                s.append(".*?\$")
                return 0
            }

            var nrOfArrangements = 1
//            val matchResult = regex.find(record)
//            if (matchResult != null) {
//                println("$record, $groups -> ${matchResult.groupValues}")
//                matchResult.groups.forEachIndexed { index, matchGroup ->
//                    if (index > 0 && matchGroup?.value == "?") {
//                        val maxIndex = if (matchResult.groups.size > index + 1) {
//                            matchResult.groups[index + 1]?.range?.first!! - 1
//                        } else {
//                            record.length
//                        }
//                        val subString = record.substring(matchGroup.range.first, maxIndex)
//                        nrOfArrangements *= subString.count { it == '?' }
//                        println("${matchGroup.value}, range = ${matchGroup.range}")
//                    }
//                }
//            }
//            println("$groups, $record, regex = $regex, nrOfArrangements = $nrOfArrangements")
            nrOfArrangements
        }
    }

    fun part2(input: List<String>, multiplier: Int): Int {
        return 0
    }

    val day = "12"
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 21) { "was ${part1(testInput)}" }
    check(part2(testInput, 10) == 0) { "was ${part2(testInput, 10)}" }

    val input = readInput("input/Day${day}")
//    part1(input).println()  // 10173804
//    part2(input, 1000000).println()
}
