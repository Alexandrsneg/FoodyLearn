package com.example.foodylearn.data.database.favorites.favorites

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodylearn.models.Result
import com.example.foodylearn.util.Constants

@Entity(tableName = Constants.FAVORITES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var recipes: Result
)