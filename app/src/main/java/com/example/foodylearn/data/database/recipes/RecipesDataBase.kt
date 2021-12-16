package com.example.foodylearn.data.database.recipes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity

@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDataBase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDAO

}