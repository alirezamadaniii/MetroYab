package com.example.metroyab.presentation.di

import com.example.metroyab.data.api.ApiService
import com.example.metroyab.data.repository.datasource.MetroRemoteDataSource
import com.example.metroyab.data.repository.datasourceimpl.MetroRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideMetroRemoteDataSource(
        apiService: ApiService
    ): MetroRemoteDataSource {
        return MetroRemoteDataSourceImpl(apiService)
    }
}