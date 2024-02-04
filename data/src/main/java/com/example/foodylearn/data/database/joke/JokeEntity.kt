package com.example.foodylearn.data.database.joke

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodylearn.data.Constants

@Entity(tableName = Constants.JOKE_TABLE)
class JokeEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @Embedded
    var joke: com.example.domain.models.FoodJokeClean
)