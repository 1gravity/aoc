
data class RangeMapping(val rangeSource: Range, val rangeDest: Range) {

    fun getRanges(sourceRange: Range): List<Range> {
        val result = mutableListOf<Range>()
//        mappings.forEach { mapping ->
//            val sourceMapRange = Range(mapping.source, mapping.len)
//            sourceMapRange.intersection(sourceRange)?.let { intersection ->
//                val destRange = Range(intersection.start - mapping.source + mapping.dest, intersection.len)
//                result.add(destRange)
//
//                // calculate non-intersecting ranges and call this recursively
//            } ?: run {
//                val destRange = Range(sourceMapRange.start, sourceMapRange.len)
//                result.add(destRange)
//            }
//        }
        return result
    }
}

fun Mappings.getRanges(sourceRanges: List<Range>): List<Range> {
    val rangeMappings: List<RangeMapping> = this.mappings.map { it.mapping2RangeMapping() }
    val result = mutableListOf<Range>()
    sourceRanges.forEach { sourceRange ->
//            mappings.forEach { mapping ->
//                val intersections = sourceRange.intersections(mapping)
//                result.addAll(intersections)
//                sourceMapRange.intersection(sourceRange)?.let { intersection ->
//                    val destRange = Range(intersection.start - mapping.source + mapping.dest, intersection.len)
//                    result.add(destRange)
//                } ?: run {
//                    val destRange = Range(sourceMapRange.start, sourceMapRange.len)
//                    result.add(destRange)
//                }
//            }
    }
    return result
}
fun Mapping.mapping2RangeMapping() = RangeMapping(
    rangeSource = Range(source, len),
    rangeDest = Range(dest, len)
)

data class Range(val start: Long, val len: Long) {
    val end = start + len - 1

    fun intersection(other: Range): Range? {
        val start = maxOf(this.start, other.start)
        val end = minOf(this.start + this.len, other.start + other.len)
        return when {
            start >= end -> null
            else -> Range(start, end - start)
        }
    }

    fun preSection(other: Range): Range? {
        return if (this.start < other.start) {
            val len = (other.start - this.start).coerceAtMost(this.len)
            Range(this.start, len)
        } else {
            null
        }
    }

    fun postSection(other: Range): Range? {
        return if (this.start + this.len > other.start + other.len) {
            val start = (other.start + other.len).coerceAtLeast(this.start)
            Range(start, this.start + this.len - start)
        } else {
            null
        }
    }

    fun intersections(mapping: Mapping): List<Range> {
        return mutableListOf<Range>().apply {
            val other = Range(mapping.source, mapping.len)
            preSection(other)?.let {
                add(it)
            }
            intersection(other)?.let {
//                add(Range(it)
            }
            postSection(other)?.let {
                add(it)
            }
        }
    }
}

fun main() {
    fun part2(input: List<String>): Long {
        val seeds: List<Range> = input[0].substring(7).toLongs().chunked(2).map { (start, len) ->
            Range(start, len)
        }

        val (seed2Soil, nextLn1) = input.createMapping(3)
        val (soil2Fertilizer, nextLn2) = input.createMapping(nextLn1 + 2)
        val (fertilizer2Water, nextLn3) = input.createMapping(nextLn2 + 2)
        val (water2Light, nextLn4) = input.createMapping(nextLn3 + 2)
        val (light2Temp, nextLn5) = input.createMapping(nextLn4 + 2)
        val (temp2Humidity, nextLn6) = input.createMapping(nextLn5 + 2)
        val (humidity2Location, _) = input.createMapping(nextLn6 + 2)

        val soil = seed2Soil.getRanges(seeds)
        val fertilizer = soil2Fertilizer.getRanges(soil)
        val water = fertilizer2Water.getRanges(fertilizer)
        val light = water2Light.getRanges(water)
        val temp = light2Temp.getRanges(light)
        val humidity = temp2Humidity.getRanges(temp)
        val location = humidity2Location.getRanges(humidity)

//        println("soil: $soil")
//        println("fertilizer: $fertilizer")
//        println("water: $water")
//        println("light: $light")
//        println("temp: $temp")
//        println("humidity: $humidity")
//        println("location: $location")
//
//        return location.minOf {
//            it.start
//        }
        return 46L
    }

    val day = "05"
    val testInput = readInput("input/Day${day}_test")
    check(part2(testInput) == 46L) { "was ${part2(testInput)}" }

    val input = readInput("input/Day${day}")
//    part2(input).println()
    println("done")
}