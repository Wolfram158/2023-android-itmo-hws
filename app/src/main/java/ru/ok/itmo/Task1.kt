package ru.ok.itmo

class Summary(private val array: Array<Int>) : Runnable {
    private var sum = 0
    override fun run() {
        for (element in array) {
            sum += element
        }
        println(sum)
    }
}

var thread1 = Thread {
    Summary(arrayOf(3, 5, 6, -1, -3, 10)).run()
}

fun main() {
    thread1.start()
    thread1.join()
}