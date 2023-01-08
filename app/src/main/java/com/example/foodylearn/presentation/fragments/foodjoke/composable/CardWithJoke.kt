package com.example.foodylearn.presentation.fragments.foodjoke.composable

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.foodylearn.R

@Composable
fun CardWithJoke(joke: String, context: Context?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White

    ) {
        Text(
            text = joke,
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(
                    state = rememberScrollState()
                ),
            fontFamily = context?.let {
                ResourcesCompat.getFont(it, R.font.courgette)?.let { typeFace ->
                    FontFamily(typeFace)
                }
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(R.color.titleColor)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    CardWithJoke("sdksnfkjsdn ssdngksngkjsn sdgnsldgnlsg", null)
}