package com.example.animeapp.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.animeapp.data.local.database.AnimeDatabase
import com.example.animeapp.data.remote.AnimeApi
import com.example.animeapp.domain.model.Hero
import com.example.animeapp.domain.model.HeroRemoteKey
import javax.inject.Inject

@ExperimentalPagingApi
class HeroRemoteMediator @Inject constructor(
    private val animeApi: AnimeApi,
    private val animeDatabase: AnimeDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = animeDatabase.heroDao()
    private val heroRemoteKeyDao = animeDatabase.heroRemoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        try {

            val response = animeApi.getAllHeroes(page =)
            if (response.heroes.isNotEmpty()) {
                animeDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeyDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val heroRemoteKeys = response.heroes.map { hero ->
                        HeroRemoteKey(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    heroRemoteKeyDao.addAllRemoteKeys(heroRemoteKeys = heroRemoteKeys)
                    heroDao.addHeroes(heroes = response.heroes)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}