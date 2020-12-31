package com.kmozcan1.docebotest.application.di

import com.kmozcan1.docebotest.BuildConfig
import com.kmozcan1.docebotest.data.api.SearchApi
import com.kmozcan1.docebotest.data.tools.GeneratedCodeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun providesRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }else{
        OkHttpClient
                .Builder()
                .build()
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        return GeneratedCodeConverters.converterFactory()
    }

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