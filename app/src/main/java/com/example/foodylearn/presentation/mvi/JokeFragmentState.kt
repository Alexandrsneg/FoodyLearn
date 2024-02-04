package com.example.foodylearn.presentation.mvi

import com.example.foodylearn.util.UiText

data class JokeFragmentState(
    val isLoading: Boolean,
    val joke: String,
    val error: UiText? = null
)
