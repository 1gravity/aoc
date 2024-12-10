data class Import(val clazz: String, val count: Int) {
    override fun toString(): String {
        return "$count -> $clazz"
    }
}

val exclude = listOf(
    "com.vivint.appcore",
    "com.vivint.sdkcore",
    "com.vivint.dynamicconfig",
    "com.vivint.constants",
    "com.vivint.vivintsky.R",
    "com.vivint.smarthome.sdk",
    "com.vivint.navigation",
    "com.vivint.vivintsky.C",
    "com.vivint.utilities.storage.SharedPreferencesProvider"
)

// to create the input file:
// grep -R --include '*.kt' "import com.vivint" . > imports.txt

val regex = "^.+:import (.+)".toRegex()
fun main() {
    readInput("input/imports")
        .map { regex.find(it)?.groupValues?.get(1) ?: "Unknown Class" } // List<Import>
        .groupBy { it }  // <Clazz, List<Clazz>>
        .mapValues { it.value.size }  // <Clazz, Int = Count>
        .map { Import(it.key, it.value) } // List<Import>
        .filter { import -> exclude.none { import.clazz.startsWith(it) } }
        .sortedByDescending { it.count }
        .writeTo("importCounts.txt")
//        .forEach {
//            println("${it.clazz} -> ${it.count}")
//        }
}
