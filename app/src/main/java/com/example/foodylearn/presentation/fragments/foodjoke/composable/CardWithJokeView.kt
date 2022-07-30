package com.example.foodylearn.presentation.fragments.foodjoke.composable

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView

class CardWithJokeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {

    var joke by mutableStateOf<String>("")

    @Composable
    override fun Content() {
        CardWithJoke(joke = joke)
    }


}