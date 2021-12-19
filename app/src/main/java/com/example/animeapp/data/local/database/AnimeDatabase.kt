package com.example.animeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animeapp.data.local.dao.HeroDao
import com.example.animeapp.domain.model.Hero

@Database(entities = [Hero::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun heroDao(): HeroDao
}