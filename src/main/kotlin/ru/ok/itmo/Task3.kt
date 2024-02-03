package ru.ok.itmo

const val x = "xxx"
const val y = "yyy"

val thread11 = Thread {
    synchronized(x) {
        println("Thread11 got x")
        synchronized(y) {
            println("Thread11 got y")
            val z = x + y
        }
    }
}

val thread2 = Thread {
    synchronized(y) {
        println("Thread2 got y")
        synchronized(x) {
            println("Thread2 got x")
            val z = x + y
        }
    }
}

fun main() {
    thread11.start()
    thread2.start()
}