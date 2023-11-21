package com.example.foodylearn.di

import com.example.domain.usecase.IGetJokeUseCase
import com.example.domain.usecase.IGetRecipesUseCase
import com.example.foodylearn.data.usecase.GetJokeUseCase
import com.example.foodylearn.data.usecase.GetRecipesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bind_GetJokeUseCase_to_IGetJokeUseCase(getJokeUseCase: GetJokeUseCase): IGetJokeUseCase
    @Binds
    fun bind_GetRecipesUseCase_to_IGetRecipesUseCase(getRecipesUseCase: GetRecipesUseCase): IGetRecipesUseCase
}