import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    //call ciadad.detectOne() with ciadad data as data and print
    val myData: List<List<Int>> = listOf(listOf(1,2),listOf(2,3),listOf(3,4),listOf(7,8))
    val myCIADADModel: ciadad = ciadad(myData)
    val result: List<Boolean> = myCIADADModel.detectOne()
    println(result)
}

class ciadad(val alldata: List<List<Int>>) {
    fun getCIADTransform(): List<Int> {
        val dvals: List<Int?> = alldata.map { getDist(it, alldata) }
        val nnDvals: List<Int> = dvals.map { it ?: -1 }
        return nnDvals
    }
    fun detectAnomalies(): List<Boolean> {
        val dvals: List<Int?> = alldata.map { getDist(it, alldata) }
        val nnDvals: List<Int> = dvals.map { it ?: -1 }
        val thresh: Int = median(dvals) ?: 0
        val res = nnDvals.map { it > thresh }
        return res
    }
    fun detectOne(usual: Boolean = false): List<Boolean> {
        val resAnomalies = detectAnomalies()
        val resUsual = resAnomalies.map {it xor usual}
        return resUsual
    }
    fun getDist(i: List<Int>, alldata: List<List<Int>>): Int? {
        val options: List<Int?> = alldata.map {getCIADist(i,it)}
        val options_not_null: List<Int> = options.map {it?: Int.MAX_VALUE}
        val closest = options_not_null.min()
        return closest
    }
    fun getCIADist(a: List<Int>, b: List<Int>): Int? {
        val dimRange = 0 until (a.size-1)
        val dimMap = dimRange.map {(a[it]-b[it]).absoluteValue}
        val ciad = dimMap.max()
        if (ciad == 0) return null
        return ciad
    }
    fun median(thing: List<Int?>): Int? {
        val medIndex = (thing.size)/2
        val medOut = thing[medIndex]
        return medOut
    }
}
