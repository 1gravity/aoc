
data class Mapping(val dest: Long, val source: Long, val len: Long)

class Mappings {
    val mappings = mutableListOf<Mapping>()
    fun addMapping(mapping: Mapping) {
        mappings.add(mapping)
    }
    fun getDestination(source: Long): Long {
        val entry = mappings.firstOrNull {
            source >= it.source && source < it.source + it.len
        }
        return when (entry) {
            null -> source
            else -> entry.dest + source - entry.source
        }
    }
}

fun String.toLongs() = this.split(" ").map { it.trim().toLong() }

fun List<String>.createMapping(start: Int): Pair<Mappings, Int> {
    val map = Mappings()
    var lineNr = start
    while (lineNr < this.size && this[lineNr].isNotBlank()) {
        val (destination, source, len) = this[lineNr].toLongs()
        map.addMapping(Mapping(destination, source, len))
        lineNr++
    }
    return Pair(map, lineNr)
}

fun main() {
    fun part1(input: List<String>): Long {
        val seeds = input[0].substring(7).toLongs()

        val (seed2Soil, nextLn1) = input.createMapping(3)
        val (soil2Fertilizer, nextLn2) = input.createMapping(nextLn1 + 2)
        val (fertilizer2Water, nextLn3) = input.createMapping(nextLn2 + 2)
        val (water2Light, nextLn4) = input.createMapping(nextLn3 + 2)
        val (light2Temp, nextLn5) = input.createMapping(nextLn4 + 2)
        val (temp2Humidity, nextLn6) = input.createMapping(nextLn5 + 2)
        val (humidity2Location, _) = input.createMapping(nextLn6 + 2)

        return seeds.minOf { seed ->
            val soil = seed2Soil.getDestination(seed)
            val fertilizer = soil2Fertilizer.getDestination(soil)
            val water = fertilizer2Water.getDestination(fertilizer)
            val light = water2Light.getDestination(water)
            val temp = light2Temp.getDestination(light)
            val humidity = temp2Humidity.getDestination(temp)
            val location = humidity2Location.getDestination(humidity)
            location
        }
    }

    val day = "05"
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 35L) { "was ${part1(testInput)}" }

    val input = readInput("input/Day${day}")
    part1(input).println()
    println("done")
}