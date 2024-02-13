package com.example.foodylearn.util.extensions


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

fun ViewModel.mainScope(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main,
    exceptionHandler: CoroutineExceptionHandler,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(coroutineDispatcher + exceptionHandler) {
        block.invoke(this)
    }
}


/**
 * this wrapper need to avoid exceptions when exception from handler become from background Thread
 * but we want to work with UI. CoroutineExceptionHandler may "shoot" at random thread, see docs
 */
inline fun ViewModel.mainSafeCoroutineExceptionHandler(crossinline handler: (CoroutineContext, Throwable) -> Unit): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            viewModelScope.launch(Dispatchers.Main) {
                handler.invoke(context, exception)
            }
        }
    }