package com.example.animeapp.data.local

import com.example.animeapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): Hero
}