package com.example.foodylearn.data.database.joke

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodylearn.models.FoodJoke
import com.example.foodylearn.util.Constants

@Entity(tableName = Constants.JOKE_TABLE)
class JokeEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var joke: FoodJoke
)