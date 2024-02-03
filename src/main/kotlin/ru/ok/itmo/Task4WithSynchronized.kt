package ru.ok.itmo

class Variable {
    private var counter = 0

    fun inc() {
        counter++
    }

    fun get(): Int {
        return counter
    }
}

@Synchronized
fun f(v : Variable) {
    for (i in 0..999) {
        v.inc()
    }
}

fun main() {
    val s = Variable()

    val thread1 = Thread {
        f(s)
    }

    val thread2 = Thread {
        f(s)
    }

    val thread3 = Thread {
        f(s)
    }

    thread1.start()
    thread2.start()
    thread3.start()

    thread1.join()
    thread2.join()
    thread3.join()

    println(s.get())
}