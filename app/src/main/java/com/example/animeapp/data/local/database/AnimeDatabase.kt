package com.example.animeapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.animeapp.data.local.converter.DatabaseConverter
import com.example.animeapp.data.local.dao.HeroDao
import com.example.animeapp.data.local.dao.HeroRemoteKeyDao
import com.example.animeapp.domain.model.Hero
import com.example.animeapp.domain.model.HeroRemoteKey

@Database(entities = [Hero::class, HeroRemoteKey::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class AnimeDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context, isUseInMemory: Boolean): AnimeDatabase {
            val databaseBuilder = if (isUseInMemory) {
                Room.inMemoryDatabaseBuilder(context, AnimeDatabase::class.java)
            } else {
                Room.databaseBuilder(context, AnimeDatabase::class.java, "test_databse.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeyDao(): HeroRemoteKeyDao
}