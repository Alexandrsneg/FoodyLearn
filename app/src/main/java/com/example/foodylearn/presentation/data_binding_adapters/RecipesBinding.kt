package com.example.foodylearn.presentation.data_binding_adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodylearn.data.models.FoodRecipes
import com.example.domain.models.NetworkResult

class RecipesBinding {

    companion object{
        @BindingAdapter("readApiResponse", "readDataBase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: com.example.domain.models.NetworkResult<FoodRecipes>?,
            dataBase: List<com.example.foodylearn.data.database.recipes.RecipesEntity>?
        ) {
            if (apiResponse is com.example.domain.models.NetworkResult.Error && dataBase.isNullOrEmpty())
                imageView.visibility = View.VISIBLE
            else if (apiResponse is com.example.domain.models.NetworkResult.Loading)
                imageView.visibility = View.INVISIBLE
            else if (apiResponse is com.example.domain.models.NetworkResult.Success)
                imageView.visibility = View.INVISIBLE

        }

        @BindingAdapter("readApiResponse2", "readDataBase2", requireAll = true)
        @JvmStatic
        fun errorTextVisibility(
            textView: TextView,
            apiResponse: com.example.domain.models.NetworkResult<FoodRecipes>?,
            dataBase: List<com.example.foodylearn.data.database.recipes.RecipesEntity>?
        ) {
            if (apiResponse is com.example.domain.models.NetworkResult.Error && dataBase.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            }
            else if (apiResponse is com.example.domain.models.NetworkResult.Loading)
                textView.visibility = View.INVISIBLE
            else if (apiResponse is com.example.domain.models.NetworkResult.Success)
                textView.visibility = View.INVISIBLE

        }
    }
}