package com.example.foodylearn.data.extensions_data

fun <T> Collection<T>?.isNotNullAndNotEmpty(): Collection<T>? =
    if (this.isNullOrEmpty()) null else this


