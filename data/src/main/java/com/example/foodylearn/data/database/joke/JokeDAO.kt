package com.example.foodylearn.data.database.joke

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJoke(jokeEntity: JokeEntity)

    @Query("SELECT * FROM joke_table ORDER BY id ASC")
    fun readJoke(): Flow<JokeEntity?>

}