package com.example.foodylearn.data.database.joke

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodylearn.data.Constants
import com.example.foodylearn.data.models.FoodJoke

@Entity(tableName = Constants.JOKE_TABLE)
class JokeEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @Embedded
    var joke: FoodJoke
)