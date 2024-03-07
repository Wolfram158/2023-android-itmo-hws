package ru.ok.itmo

class Sum(private val array : Array<Int>) : Runnable {
    private var s = 0
    override fun run() {
        for (element in array) {
            s += element
        }
    }

    fun getS() : Int {
        return s
    }
}

fun main() {
    val array = arrayOf(1, -2, -3, 4, -5, 6, 70, -8)
    val sum1 = Sum(array.sliceArray(0..< array.size / 2))
    val sum2 = Sum(array.sliceArray(array.size / 2..< array.size))
    val th1 = Thread(sum1)
    val th2 = Thread(sum2)
    th1.start()
    th2.start()
    th1.join()
    th2.join()
    println(sum1.getS() + sum2.getS())
}