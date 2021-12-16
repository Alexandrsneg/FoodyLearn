package com.example.foodylearn.data

import com.example.foodylearn.data.database.favorites.favorites.FavoritesDAO
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.data.database.recipes.RecipesDAO
import com.example.foodylearn.data.database.recipes.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO,
    private val favoritesDAO: FavoritesDAO
) {


    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDAO.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return  favoritesDAO.readFavorites()
    }

    suspend fun insertFavorite(favoritesEntity: FavoritesEntity) {
        favoritesDAO.insertFavorites(favoritesEntity)
    }

    suspend fun deleteFavorite(favoritesEntity: FavoritesEntity){
        favoritesDAO.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavorites(){
        favoritesDAO.deleteAllFavoriteRecipes()
    }
}