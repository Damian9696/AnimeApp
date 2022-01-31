package com.example.animeapp.domain.use_cases.save_onboarding

import com.example.animeapp.data.repository.Repository

class SaveOnBoardingUseCase constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed = completed)
    }
}