package com.example.foodylearn.data

import com.example.foodylearn.data.database.favorites.favorites.FavoritesDAO
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.data.database.joke.JokeDAO
import com.example.foodylearn.data.database.joke.JokeEntity
import com.example.foodylearn.data.database.recipes.RecipesDAO
import com.example.foodylearn.data.database.recipes.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO,
    private val favoritesDAO: FavoritesDAO,
    private val jokeDao: JokeDAO
) {


    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDAO.readRecipes()
    }

    fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return  favoritesDAO.readFavorites()
    }

    fun insertFavorite(favoritesEntity: FavoritesEntity) {
        favoritesDAO.insertFavorites(favoritesEntity)
    }

    suspend fun deleteFavorite(favoritesEntity: FavoritesEntity){
        favoritesDAO.deleteFavoriteRecipe(favoritesEntity)
    }

    fun deleteAllFavorites(){
        favoritesDAO.deleteAllFavoriteRecipes()
    }

    fun readJoke(): Flow<JokeEntity?> = jokeDao.readJoke()

    suspend fun insertJoke(jokeEntity: JokeEntity) = jokeDao.insertJoke(jokeEntity)

}