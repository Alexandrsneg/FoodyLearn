package com.example.foodylearn.util

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(lifecycleOwner, object: Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}


inline fun <reified T> Activity.startActivity(block: (Intent)-> Unit) {
    startActivity(
        Intent(this, T::class.java).apply {
            block(this)
        }
    )
}

fun LifecycleOwner.repeatOnLifecycleExt(state: Lifecycle.State, block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(state) {
            block.invoke(this)
        }
    }
}