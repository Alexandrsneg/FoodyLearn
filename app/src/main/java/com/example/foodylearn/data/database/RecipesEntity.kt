package com.example.foodylearn.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodylearn.models.FoodRecipe
import com.example.foodylearn.util.Constants

@Entity(tableName = Constants.RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}