package com.example.foodylearn.data.database.favorites.favorites

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(recipesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorites_table ORDER BY id ASC")
    suspend fun readFavorites(): List<FavoritesEntity>

    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity): Int

    @Query("DELETE FROM favorites_table WHERE id = :userId")
    suspend fun deleteById(userId: Int)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavoriteRecipes(): Int
}