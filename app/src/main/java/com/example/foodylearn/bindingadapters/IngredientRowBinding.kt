package com.example.foodylearn.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

class IngredientRowBinding {

    companion object {
        @BindingAdapter("setAmount")
        @JvmStatic
        fun setAmount(textView: TextView, count: Double){
            textView.text = count.toString()
        }
    }
}