package com.example.foodylearn.util.extensions

import android.content.Context
import androidx.annotation.StringRes

fun Context.stringByRes(@StringRes id: Int): String = getString(id)