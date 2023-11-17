package com.example.foodylearn.data.database.favorites.favorites

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.foodylearn.data.models.Recipe

class FavoritesTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipes: Recipe): String {
        return gson.toJson(foodRecipes)
    }

    @TypeConverter
    fun stringToResult(data: String): Recipe {
        val listType = object : TypeToken<Recipe>() {}.type
        return gson.fromJson(data, listType)
    }
}