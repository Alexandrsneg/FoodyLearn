package com.example.foodylearn.data.repositories

import com.example.domain.models.DataSourceResponseWrapper
import com.example.domain.models.FoodJokeClean
import com.example.domain.models.FoodRecipesClean
import com.example.domain.models.Source
import com.example.domain.usecase.IRepository
import com.example.foodylearn.data.data_sources.LocalDataSource
import com.example.foodylearn.data.data_sources.RemoteDataSource
import com.example.foodylearn.data.toFoodRecipesClean
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IRepository {
    override suspend fun getRecipes(queries: Map<String, String>): DataSourceResponseWrapper<FoodRecipesClean> {
        return try {
            DataSourceResponseWrapper(remoteDataSource.getRecipes(queries), source = Source.REMOTE)
        } catch (e: Exception) {
            DataSourceResponseWrapper(
                localDataSource.readRecipes().foodRecipes,
                source = Source.LOCAL
            )
        }
    }

    override suspend fun getJoke(): DataSourceResponseWrapper<FoodJokeClean> {
        return try {
            DataSourceResponseWrapper(remoteDataSource.getJoke(), source = Source.REMOTE)
        } catch (e: Exception) {
            DataSourceResponseWrapper(localDataSource.readJoke()?.joke, source = Source.LOCAL)
        }
    }

    override suspend fun getFavoriteRecipes(): DataSourceResponseWrapper<FoodRecipesClean> =
        DataSourceResponseWrapper(
            localDataSource.readFavoriteRecipes().toFoodRecipesClean(),
            source = Source.LOCAL
        )

}