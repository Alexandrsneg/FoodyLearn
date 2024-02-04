package com.example.foodylearn

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.NullPointerException
import java.util.concurrent.atomic.AtomicInteger

val handler = CoroutineExceptionHandler { _, e ->
    if (e is CancellationException) {
        println("rethrow ${e.message}")
        throw e
    }
    else
     println("Catched ${e.message}")
}
val superJob = SupervisorJob()
val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default + handler)


val mutex = Mutex()
var atomicCounter = AtomicInteger(0)
var counter = 0


val ids = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)

fun main()   {
//    val photoRepositoryImpl = SomeRepositoryImpl()
//
//    coroutineScope.launch {
//        val result = photoRepositoryImpl.fetchPhotosByIds(ids)
//        println("photo Result = $result")
//    }.join()
//    superJobsTest(coroutineScope)

    val whenPrint = "before"
    if (whenPrint != "after" && whenPrint != "before") {
        println("STOPPED")
    }
    println("fin")
}

//fun main() = runBlocking {
//    coroutineScope.launch {
//        count()
//    }.join()
//}

suspend fun superJobsTest(coroutineScope: CoroutineScope) {
        withContext(Dispatchers.IO + handler) {
            println("superJobMain start")
            delay(1000)

            launch(SupervisorJob()) {
                println("A start")

                launch() {
                    println("B child start")
                    delay(1000)
                    println("B exception delenie")
                    val delene = 2 / 0
                    println("B child  finish")

                    launch {
                        println("C child start")
                        delay(1000)
                        println("C child  finish")
                    }
                }.join()

                println("A finish")

            }.join()


            val secondChildJob = launch {
                println("D start")
                delay(1000)
                println("D finish")
            }.join()


            println("SuperJob finish")
        }

}


suspend fun count() {
    withContext(Dispatchers.IO) {
        val defferedList = mutableListOf<Job>()

        for (i in 0..10000)
            defferedList.add(
                launch {
                    incrementCounterWithMutex()
                }
            )

        defferedList.joinAll()
        println("total = $counter")
    }
}

suspend fun incrementCounterWithChannel(actor: SendChannel<Int>) {
    actor.send(1)
}

suspend fun incrementCounterWithMutex() {
    delay(1000)
    mutex.withLock {
        counter++
    }
    println(counter)
}

suspend fun incrementCounterAtomic() {
    delay(1000)
    atomicCounter.getAndIncrement()
    println(atomicCounter)
}

suspend fun incrementCounter() {
    counter++
    println(counter)
}


suspend fun getUsersPhotos(ids: List<Int>): List<String> {
    return try {
        withContext(Dispatchers.IO + CoroutineExceptionHandler { _, e ->
            println("Catched inside")
        }) {
            supervisorScope {
                val listOfPhotos = mutableListOf<String>()
                val jobList: List<Job> = ids.map {
                    if (it % 2 == 0)
                        launch {
                            listOfPhotos.add(getUserPhoto(it))
                        }
                    else
                        launch {
                            listOfPhotos.add(getUserPhotoLong(it))
                        }
                }
                jobList.joinAll()
                listOfPhotos
            }
        }
    } catch (e: Exception) {
        listOf<String>("cathed inner error")
    }
}

suspend fun getUsersPhotosAwaitStyle(ids: List<Int>): List<String> {
    return withContext(Dispatchers.IO) {
        supervisorScope {
            val asyncs = ids.map {
                if (it % 2 == 0)
                    async { getUserPhoto(it) }
                else
                    async { getUserPhotoLong(it) }
            }
            try {
                asyncs.awaitAll()
            } catch (e: Exception) {
                listOf("asuncError")
            }
        }
    }
}

suspend fun getUserPhoto(id: Int): String {
    throw NullPointerException()
    println("getUserPhoto id:$id, finish")
    return "UserPhoto ($id)"
}

suspend fun getUserPhotoLong(id: Int): String {
    delay(1000)
    println("getUserPhotoLong id:$id, finish")
    return "UserPhoto ($id)"
}


suspend fun oper1() {
    println("oper1")
    delay(1000)
    println("oper1 finish")
}

suspend fun oper2Npe() {
    println("oper2")
    throw NullPointerException()
    delay(1000)
    println("oper2 finish")
}

suspend fun oper3() {
    println("oper3")
    delay(1000)
    println("oper3 finish")
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

inline fun <reified T> Activity.startActivity(block: (Intent) -> Unit) {
    startActivity(
        Intent(this, T::class.java).apply {
            block(this)
        }
    )
}

val ld = MutableLiveData<String>()
val _ld: LiveData<String> = ld

inline fun <reified T> MutableLiveData<T>.changeState(block: (T) -> T) {
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

inline fun <reified T> T.invokePrivatePrintData() {
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