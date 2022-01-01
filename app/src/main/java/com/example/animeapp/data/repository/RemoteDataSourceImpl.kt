package com.example.animeapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animeapp.data.local.database.AnimeDatabase
import com.example.animeapp.data.pagingsource.HeroRemoteMediator
import com.example.animeapp.data.pagingsource.SearchHeroesSource
import com.example.animeapp.data.remote.AnimeApi
import com.example.animeapp.data.remote.RemoteDataSource
import com.example.animeapp.domain.model.Hero
import com.example.animeapp.util.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val animeApi: AnimeApi,
    private val animeDatabase: AnimeDatabase
) : RemoteDataSource {

    private val heroDao = animeDatabase.heroDao()

    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { heroDao.getAllHeroes() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = HeroRemoteMediator(
                animeApi = animeApi,
                animeDatabase = animeDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SearchHeroesSource(
                    animeApi = animeApi,
                    query = query
                )
            }
        ).flow
    }
}