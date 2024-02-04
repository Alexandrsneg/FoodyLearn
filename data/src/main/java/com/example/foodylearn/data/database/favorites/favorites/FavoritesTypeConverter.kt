package com.example.foodylearn.data.database.favorites.favorites

import androidx.room.TypeConverter
import com.example.domain.models.FoodRecipesClean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.foodylearn.data.models.Recipe

class FavoritesTypeConverter {

    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun foodRecipeToString(foodRecipes: Recipe): String {
        return gson.toJson(foodRecipes)
    }


    @TypeConverter
    fun stringToRecipe(data: String): Recipe {
        val listType = object : TypeToken<Recipe>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun foodRecipesCleanToString(foodRecipes: FoodRecipesClean): String {
        return gson.toJson(foodRecipes)
    }


    @TypeConverter
    fun stringToFoodRecipesClean(data: String): FoodRecipesClean {
        val listType = object : TypeToken<FoodRecipesClean>() {}.type
        return gson.fromJson(data, listType)
    }
}