package com.example.foodylearn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

fun main() {

    val test = null
    val param = "before"

    test?.let {
        if (param == "before")
            println("before")
    } ?: run {
        println("run")
        return
    }

    println("finish")
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

inline fun <reified T> Activity.startActivity(block: (Intent)-> Unit) {
    startActivity(
        Intent(this, T::class.java).apply {
            block(this)
        }
    )
}

 val ld = MutableLiveData<String>()
 val _ld: LiveData<String> = ld

inline fun <reified T> MutableLiveData<T>.changeState(block:(T)->T) {
    value?.let {
        val newState = block.invoke(it)
        value = newState
    }
}

fun doSmt(name: String) {
    ld.changeState<String> {
        val sm = 10 + 10
        val newName = name + "ssss" + it
        newName
    }
}

inline fun <reified T>T.invokePrivatePrintData() {
    T::class.java.declaredMethods.firstOrNull { it.name == "printPrivate" }?.let {
        it.isAccessible = true
        it.invoke(this)
    }
}

class Test() {
    private fun printPrivate() {
        print("Print private")
    }

    fun printPublic() {
        print("Print public")
    }
}