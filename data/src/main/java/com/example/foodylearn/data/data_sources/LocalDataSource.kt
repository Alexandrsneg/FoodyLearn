package com.example.foodylearn.data.data_sources

import com.example.foodylearn.data.database.favorites.favorites.FavoritesDAO
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.data.database.joke.JokeDAO
import com.example.foodylearn.data.database.joke.JokeEntity
import com.example.foodylearn.data.database.recipes.RecipesDAO
import com.example.foodylearn.data.database.recipes.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO,
    private val favoritesDAO: FavoritesDAO,
    private val jokeDao: JokeDAO
) {

    // ****************** READ ****************
    suspend fun readRecipes(): RecipesEntity = recipesDAO.readRecipes()
    suspend fun readFavoriteRecipes(): List<FavoritesEntity> = favoritesDAO.readFavorites()
    suspend fun readJoke(): JokeEntity? = jokeDao.readJoke()


    // ****************** INSERT ****************
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }

    suspend fun insertFavorite(favoritesEntity: FavoritesEntity) {
        favoritesDAO.insertFavorites(favoritesEntity)
    }

    suspend fun insertJoke(jokeEntity: JokeEntity) {
        jokeDao.insertJoke(jokeEntity)
    }

    // ****************** DELETE ****************
    suspend fun deleteFavorite(recipeId: Int) {
        favoritesDAO.deleteById(recipeId)
    }

    suspend fun deleteAllFavorites() {
        favoritesDAO.deleteAllFavoriteRecipes()
    }

}