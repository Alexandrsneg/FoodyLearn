package com.example.foodylearn.di

import android.content.Context
import androidx.room.Room
import com.example.foodylearn.data.database.recipes.RecipesDataBase
import com.example.foodylearn.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RecipesDataBase {
        return Room.databaseBuilder(
            context,
            RecipesDataBase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRecipesDao(dataBase: RecipesDataBase) = dataBase.recipesDao()

    @Singleton
    @Provides
    fun provideFavoritesDao(dataBase: RecipesDataBase) = dataBase.favoritesDao()

}