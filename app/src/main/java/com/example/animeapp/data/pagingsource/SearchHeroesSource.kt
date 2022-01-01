package com.example.animeapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animeapp.data.remote.AnimeApi
import com.example.animeapp.domain.model.Hero
import javax.inject.Inject

class SearchHeroesSource @Inject constructor(
    private val animeApi: AnimeApi,
    private val query: String
) : PagingSource<Int, Hero>() {

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        return try {

            val apiResponse = animeApi.searchHeroes(query = query)
            val heroes = apiResponse.heroes
            if (heroes.isNotEmpty()) {
                LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}