package ru.ok.itmo

fun g(v : Variable) {
    for (i in 0..999) {
        v.inc()
    }
}

fun main() {
    val s = Variable()

    val thread1 = Thread {
        g(s)
    }

    val thread2 = Thread {
        g(s)
    }

    val thread3 = Thread {
        g(s)
    }

    thread1.start()
    thread2.start()
    thread3.start()

    thread1.join()
    thread2.join()
    thread3.join()

    println(s.get())
}