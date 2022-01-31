package com.example.animeapp.domain.use_cases.read_onboarding

import com.example.animeapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}