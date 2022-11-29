package com.example.foodylearn.presentation.data_binding_adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foodylearn.R
import com.example.foodylearn.util.Constants

class IngredientRowBinding {

    companion object {
        @BindingAdapter("setAmount")
        @JvmStatic
        fun setAmount(textView: TextView, count: Double){
            textView.text = count.toString()
        }

        @BindingAdapter("loadIngredientImageFromUrl")
        @JvmStatic
        fun loadIngredientImageFromUrl(imageView: ImageView, imageUrl: String?) {
            imageView.load(Constants.BASE_IMAGES_URL + imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }
}