package com.example.foodylearn.presentation.data_binding_adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodylearn.data.models.FoodRecipes
import com.example.domain.models.NetworkResult
import com.example.foodylearn.data.database.recipes.RecipesEntity

class RecipesBinding {

    companion object {
        @BindingAdapter("readApiResponse", "readDataBase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipes>,
            dataBase: List<RecipesEntity>?
        ) {
            val visibility = when (apiResponse) {
                is NetworkResult.Error -> if (dataBase.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
                is NetworkResult.Success -> View.INVISIBLE
            }

            imageView.visibility = visibility
        }

        @BindingAdapter("readApiResponse2", "readDataBase2", requireAll = true)
        @JvmStatic
        fun errorTextVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipes>?,
            dataBase: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && dataBase.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            }
            else if (apiResponse is NetworkResult.Success)
                textView.visibility = View.INVISIBLE

        }
    }
}