package com.example.animeapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted

    init {
        viewModelScope.launch {
            _onBoardingCompleted.value =
                useCases.readOnBoardingUseCase()
                    .stateIn(scope = viewModelScope)
                    .value
        }
    }

}