package com.example.foodylearn.util.extensions

fun <T> Collection<T>?.isNotNullAndNotEmpty(): Collection<T>? =
    if (this.isNullOrEmpty()) null else this


