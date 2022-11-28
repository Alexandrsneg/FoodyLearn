package com.example.foodylearn.util

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

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