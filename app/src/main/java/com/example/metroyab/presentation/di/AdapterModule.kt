package com.example.metroyab.presentation.di

import com.example.metroyab.presentation.adapter.NearestMetroAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideNearestAdapter(): NearestMetroAdapter {
        return NearestMetroAdapter()
    }

}