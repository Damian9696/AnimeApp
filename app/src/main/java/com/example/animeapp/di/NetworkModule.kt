package com.example.animeapp.di

import androidx.paging.ExperimentalPagingApi
import com.example.animeapp.BuildConfig
import com.example.animeapp.data.local.database.AnimeDatabase
import com.example.animeapp.data.remote.AnimeApi
import com.example.animeapp.data.remote.RemoteDataSource
import com.example.animeapp.data.repository.RemoteDataSourceImpl
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

@ExperimentalPagingApi
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
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(mediaType))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
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

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        animeApi: AnimeApi,
        animeDatabase: AnimeDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(animeApi = animeApi, animeDatabase = animeDatabase)
    }
}