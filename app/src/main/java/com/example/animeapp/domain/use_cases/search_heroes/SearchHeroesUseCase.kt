package com.example.animeapp.domain.use_cases.search_heroes

import androidx.paging.PagingData
import com.example.animeapp.data.repository.Repository
import com.example.animeapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class SearchHeroesUseCase(
    private val repository: Repository
) {

    operator fun invoke(query: String): Flow<PagingData<Hero>> {
        return repository.searchHeroes(query = query)
    }

}