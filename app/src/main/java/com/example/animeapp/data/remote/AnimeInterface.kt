package com.example.animeapp.data.remote

import com.example.animeapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeInterface {

    @GET("/anime/heroes")
    suspend fun getAllHeroes(@Query("page") page: Int = 1): ApiResponse

    @GET("/anime/heroes/search")
    suspend fun searchHeroes(@Query("query") query: String): ApiResponse
}