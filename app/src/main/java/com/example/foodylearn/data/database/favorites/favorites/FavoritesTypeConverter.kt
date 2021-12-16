package com.example.foodylearn.data.database.favorites.favorites

import androidx.room.TypeConverter
import com.example.foodylearn.models.FoodRecipes
import com.example.foodylearn.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipes: Result): String {
        return gson.toJson(foodRecipes)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listType)
    }
}