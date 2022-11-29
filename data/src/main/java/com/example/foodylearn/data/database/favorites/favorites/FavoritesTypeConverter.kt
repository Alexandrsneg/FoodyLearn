package com.example.foodylearn.data.database.favorites.favorites

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.foodylearn.data.models.Result

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