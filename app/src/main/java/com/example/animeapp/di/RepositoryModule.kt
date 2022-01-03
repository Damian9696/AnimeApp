package com.example.animeapp.di

import android.content.Context
import com.example.animeapp.data.local.LocalDataSource
import com.example.animeapp.data.local.database.AnimeDatabase
import com.example.animeapp.data.local.preferences.DataStoreOperations
import com.example.animeapp.data.local.preferences.DataStoreOperationsImpl
import com.example.animeapp.data.repository.LocalDataSourceImpl
import com.example.animeapp.data.repository.Repository
import com.example.animeapp.domain.use_cases.UseCases
import com.example.animeapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.example.animeapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.example.animeapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.animeapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.animeapp.domain.use_cases.search_heroes.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context: Context): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository = repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository = repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository = repository),
            searchHeroesUseCase = SearchHeroesUseCase(repository = repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        animeDatabase: AnimeDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(animeDatabase = animeDatabase)
    }
}