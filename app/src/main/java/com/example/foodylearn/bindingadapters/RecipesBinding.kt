package com.example.foodylearn.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodylearn.data.database.recipes.RecipesEntity
import com.example.foodylearn.models.FoodRecipes
import com.example.foodylearn.util.NetworkResult

class RecipesBinding {

    companion object{
        @BindingAdapter("readApiResponse", "readDataBase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipes>?,
            dataBase: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && dataBase.isNullOrEmpty())
                imageView.visibility = View.VISIBLE
            else if (apiResponse is NetworkResult.Loading)
                imageView.visibility = View.INVISIBLE
            else if (apiResponse is NetworkResult.Success)
                imageView.visibility = View.INVISIBLE

        }

        @BindingAdapter("readApiResponse2", "readDataBase2", requireAll = true)
        @JvmStatic
        fun errorTextVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipes>?,
            dataBase: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && dataBase.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            }
            else if (apiResponse is NetworkResult.Loading)
                textView.visibility = View.INVISIBLE
            else if (apiResponse is NetworkResult.Success)
                textView.visibility = View.INVISIBLE

        }
    }
}