package com.kmozcan1.docebotest.application

import com.kmozcan1.docebotest.data.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import javax.inject.Singleton

/**
 * Created by Kadir Mert Özcan on 28-Dec-20.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private var url = HttpUrl.Builder()
        .scheme("https")
        .host("api.github.com")
        .addPathSegment("") // to add "/" at the end
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(
        converterFactory: Converter.Factory,
        callAdapterFactory: RxJava3CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchApi(
        retrofit: Retrofit
    ): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

}