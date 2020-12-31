package com.kmozcan1.docebotest.application.di

import com.kmozcan1.docebotest.data.api.SearchApi
import com.kmozcan1.docebotest.data.repository.SearchRepositoryImpl
import com.kmozcan1.docebotest.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Kadir Mert Özcan on 30-Dec-20.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideSearchRepository(
            searchApi: SearchApi
    ): SearchRepository = SearchRepositoryImpl(searchApi)
}