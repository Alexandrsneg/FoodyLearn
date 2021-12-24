package com.example.foodylearn.data.database.recipes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodylearn.data.database.favorites.favorites.FavoritesDAO
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.data.database.favorites.favorites.FavoritesTypeConverter

@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class, FavoritesTypeConverter::class)
abstract class RecipesDataBase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDAO
    abstract fun favoritesDao(): FavoritesDAO

}