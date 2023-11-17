package com.example.foodylearn.data.database.favorites.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodylearn.data.Constants
import com.example.foodylearn.data.models.Recipe

@Entity(tableName = Constants.FAVORITES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var recipes: Recipe
)