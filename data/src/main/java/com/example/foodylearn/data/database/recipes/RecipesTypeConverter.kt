package com.example.foodylearn.data.database.recipes

import androidx.room.TypeConverter
import com.example.foodylearn.data.models.FoodRecipes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipesRest: FoodRecipes): String {
        return gson.toJson(foodRecipesRest)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipes {
        val listType = object : TypeToken<FoodRecipes>() {}.type
        return gson.fromJson(data, listType)
    }
}