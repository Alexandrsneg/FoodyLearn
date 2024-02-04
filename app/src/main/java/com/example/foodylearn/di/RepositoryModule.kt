package com.example.foodylearn.di

import com.example.domain.usecase.IRepository
import com.example.foodylearn.data.data_sources.LocalDataSource
import com.example.foodylearn.data.data_sources.RemoteDataSource
import com.example.foodylearn.data.repositories.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): IRepository = Repository(remoteDataSource, localDataSource)

}