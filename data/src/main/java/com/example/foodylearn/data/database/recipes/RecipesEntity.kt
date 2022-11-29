package com.example.foodylearn.data.database.recipes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodylearn.data.Constants
import com.example.foodylearn.data.models.FoodRecipes

@Entity(tableName = Constants.RECIPES_TABLE)
class RecipesEntity(
    var foodRecipes: FoodRecipes
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}