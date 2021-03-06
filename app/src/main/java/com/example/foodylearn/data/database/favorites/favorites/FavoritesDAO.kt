package com.example.foodylearn.data.database.favorites.favorites

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(recipesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorites_table ORDER BY id ASC")
    fun readFavorites(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavoriteRecipes()
}