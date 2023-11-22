package com.example.foodylearn.util.extensions


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.mainScope(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main,
    exceptionHandler: CoroutineExceptionHandler,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(coroutineDispatcher + exceptionHandler) {
        block.invoke(this)
    }
}