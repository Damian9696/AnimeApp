package com.example.animeapp.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.MediatorResult
import androidx.test.core.app.ApplicationProvider
import com.example.animeapp.data.local.database.AnimeDatabase
import com.example.animeapp.data.remote.FakeAnimeApi2
import com.example.animeapp.domain.model.Hero
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class HeroRemoteMediatorTest {

    private lateinit var animeApi: FakeAnimeApi2
    private lateinit var animeDatabase: AnimeDatabase

    @Before
    fun setup() {
        animeApi = FakeAnimeApi2()
        animeDatabase = AnimeDatabase.create(
            context = ApplicationProvider.getApplicationContext(),
            isUseInMemory = true
        )
    }

    @After
    fun cleanup() {
        animeDatabase.clearAllTables()
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnSuccessResultWhenMoreDataIsNotPresent() =
        runBlocking {
            val remoteMediator = HeroRemoteMediator(
                animeApi = animeApi,
                animeDatabase = animeDatabase
            )
            val pagingState = PagingState<Int, Hero>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(
                loadType = LoadType.REFRESH,
                state = pagingState
            )

            assertTrue(result is MediatorResult.Success)
            assertFalse((result as MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadSuccessAndEndOfPaginationTrueWhenNoMoreData() =
        runBlocking {
            animeApi.clearData()
            val remoteMediator = HeroRemoteMediator(
                animeApi = animeApi,
                animeDatabase = animeDatabase
            )
            val pagingState = PagingState<Int, Hero>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(
                loadType = LoadType.REFRESH,
                state = pagingState
            )

            assertTrue(result is MediatorResult.Success)
            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() =
        runBlocking {
            animeApi.addException()
            val remoteMediator = HeroRemoteMediator(
                animeApi = animeApi,
                animeDatabase = animeDatabase
            )
            val pagingState = PagingState<Int, Hero>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(
                loadType = LoadType.REFRESH,
                state = pagingState
            )

            assertTrue(result is MediatorResult.Error)
        }
}