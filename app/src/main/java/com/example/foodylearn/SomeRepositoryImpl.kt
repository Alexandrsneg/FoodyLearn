package com.example.foodylearn

import android.net.http.HttpException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.Collections

class SomeRepositoryImpl {

    val mutex = Mutex()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("catched with handler: ${throwable.message}")
    }
    val corScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)


    suspend fun fetchPhotosByIds(ids: List<Int>): List<String> {
        return try {
            withContext(Dispatchers.IO) {
                val defferedPhotos = mutableListOf<Deferred<String>>()
                val listOfPhotos = mutableListOf<String>()
                ids.forEach {
                    defferedPhotos.add(
                        corScope.async {
                            photoApi(it)
                        }
                    )
                }

                defferedPhotos.forEach {
                    try {
                        listOfPhotos.add(it.await())
                    } catch (e: Exception) {
                        println("catched await ${e.message}")
                    }
                }

                listOfPhotos
            }
        } catch (e: Exception) {
            listOf("${e.message}")
        }
    }


    suspend fun addToListSafely(element: String) {
        mutex.withLock {
//            listOfPhotos.add(element)
        }

    }
}


suspend fun photoApi(id:Int) : String {
    println("try fetch")
    delay(1000)
    return if (id.even())
        throw Exception("apiException")
    else
        "Photo $id"
}


fun Int.even() = this%2 == 0