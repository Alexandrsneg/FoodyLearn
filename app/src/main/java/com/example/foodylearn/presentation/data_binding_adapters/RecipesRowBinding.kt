package com.example.foodylearn.presentation.data_binding_adapters

import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foodylearn.R
import com.example.foodylearn.models.Result
import com.example.foodylearn.presentation.fragments.recipes.RecipesFragmentDirections

class RecipesRowBinding {

    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result) {
            recipeRowLayout.setOnClickListener {
                try {
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                } catch (e: Exception){
                    Log.d("RecipesRowBinding", e.toString())
                }
            }

        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        @BindingAdapter("setHtmlFormattedText")
        @JvmStatic
        fun setHtmlFormattedText(textView: TextView, text: String){
            textView.text = Html.fromHtml(text)
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, isVegan: Boolean){
            if (isVegan){
                when(view){
                    is TextView -> view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    is ImageView -> view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                }
            }
        }
    }
}