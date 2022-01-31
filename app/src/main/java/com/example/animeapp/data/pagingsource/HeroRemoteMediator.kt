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
import com.example.animeapp.util.Constants.CACHE_TIMEOUT
import com.example.animeapp.util.Constants.MILLI_TO_HOUR_UNIT
import javax.inject.Inject

@ExperimentalPagingApi
class HeroRemoteMediator @Inject constructor(
    private val animeApi: AnimeApi,
    private val animeDatabase: AnimeDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = animeDatabase.heroDao()
    private val heroRemoteKeyDao = animeDatabase.heroRemoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = heroRemoteKeyDao.getRemoteKey(id = 1)?.lastUpdated ?: 0L
        val cacheTimeout = CACHE_TIMEOUT

        val diffInHours = (currentTime - lastUpdated) / MILLI_TO_HOUR_UNIT
        return if (diffInHours.toInt() <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    nextPage
                }
            }

            val response = animeApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                animeDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeyDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val lastUpdated = response.lastUpdated
                    val heroRemoteKeys = response.heroes.map { hero ->
                        HeroRemoteKey(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = lastUpdated
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


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Hero>
    ): HeroRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeyDao.getRemoteKey(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Hero>): HeroRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { hero ->
            heroRemoteKeyDao.getRemoteKey(id = hero.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Hero>): HeroRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { hero ->
            heroRemoteKeyDao.getRemoteKey(id = hero.id)
        }
    }

//    private fun parseMillis(millis: Long): String {
//        val date = Date(millis)
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ROOT)
//        return format.format(date)
//    }
}