package ru.ok.itmo

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.math.pow

class Power(private val number : Double, private val power : Double) : Callable<Double> {
    override fun call(): Double {
        return number.pow(power)
    }
}

fun main() {
    val service : ExecutorService = Executors.newFixedThreadPool(8)
    val callableTask : Callable<Double> = Power(2.0, 9.0)
    val future : Future<Double> = service.submit(callableTask)
    val result : Double? = future.get()
    println(result)
    service.shutdown()
}