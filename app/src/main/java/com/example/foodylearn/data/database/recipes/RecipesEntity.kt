package com.example.foodylearn.data.database.recipes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodylearn.models.FoodRecipes
import com.example.foodylearn.util.Constants

@Entity(tableName = Constants.RECIPES_TABLE)
class RecipesEntity(
    var foodRecipes: FoodRecipes
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}