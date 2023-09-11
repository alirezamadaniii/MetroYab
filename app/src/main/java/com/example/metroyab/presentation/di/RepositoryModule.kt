package com.example.metroyab.presentation.di

import com.example.metroyab.data.repository.MetroRepositoryImpl
import com.example.metroyab.data.repository.datasource.MetroRemoteDataSource
import com.example.metroyab.domaim.repository.MetroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMetroRepository(
        metroRemoteDataSource: MetroRemoteDataSource
    ):MetroRepository{
        return MetroRepositoryImpl(
            metroRemoteDataSource)
    }
}