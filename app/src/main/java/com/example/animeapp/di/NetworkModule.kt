package com.example.animeapp.di

import com.example.animeapp.data.remote.AnimeApi
import com.example.animeapp.util.Constants.BASE_URL
import com.example.animeapp.util.Constants.CONTENT_TYPE
import com.example.animeapp.util.Constants.TIME_OUT
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAnimeApi(retrofit: Retrofit): AnimeApi {
        return retrofit.create(AnimeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, mediaType: MediaType): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(mediaType))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(TIME_OUT, TimeUnit.MINUTES)
            .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideMediaType(contentType: String): MediaType {
        return MediaType.get(contentType)
    }

    @Provides
    @Singleton
    fun provideContentType(): String {
        return CONTENT_TYPE
    }
}