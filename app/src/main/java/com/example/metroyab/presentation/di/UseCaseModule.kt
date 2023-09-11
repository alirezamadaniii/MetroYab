package com.example.metroyab.presentation.di

import com.example.metroyab.domaim.repository.MetroRepository
import com.example.metroyab.domaim.usecase.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideAuthUseCase(
        metroRepository: MetroRepository
    ):SearchUseCase{
        return SearchUseCase(metroRepository)
    }


}