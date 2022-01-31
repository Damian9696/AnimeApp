package com.example.animeapp.domain.use_cases.get_selected_hero

import com.example.animeapp.data.repository.Repository
import com.example.animeapp.domain.model.Hero

class GetSelectedHeroUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(heroId: Int): Hero {
        return repository.getSelectedHero(heroId = heroId)
    }

}