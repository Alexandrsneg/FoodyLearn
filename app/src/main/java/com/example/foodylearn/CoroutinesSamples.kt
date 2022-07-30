package com.example.foodylearn

import kotlinx.coroutines.*

fun main(): Unit = runBlocking {
    var res1 = async {
        longOperationA()
    }
    var res2 = async {
        longOperationB()
    }

    println(res1.await() + res2.await())
}

suspend fun longOperationA(): Int {
    println("longOperation A start")
    delay(3000)
    println("longOperation A finish")
    return 3
}

suspend fun longOperationB(): Int {
    println("longOperation B start")
    delay(1000)
    println("longOperation B finish")
    return 1
}