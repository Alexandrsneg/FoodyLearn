package com.example.domain.models

class DataSourceResponseWrapper<T>(
    val data: T? = null,
    val source: Source
)

enum class Source {
    LOCAL, REMOTE
}