package com.example.animeapp.data.repository

import androidx.paging.PagingData
import com.example.animeapp.data.local.LocalDataSource
import com.example.animeapp.data.local.preferences.DataStoreOperations
import com.example.animeapp.data.remote.RemoteDataSource
import com.example.animeapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataStore: DataStoreOperations,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

    fun getAllHeroes(): Flow<PagingData<Hero>> {
        return remoteDataSource.getAllHeroes()
    }

    fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return remoteDataSource.searchHeroes(query = query)
    }

    suspend fun getSelectedHero(heroId: Int): Hero {
        return localDataSource.getSelectedHero(heroId = heroId)
    }

}