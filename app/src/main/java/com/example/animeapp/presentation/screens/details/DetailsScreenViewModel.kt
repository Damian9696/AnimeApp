package com.example.animeapp.presentation.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.model.Hero
import com.example.animeapp.domain.use_cases.UseCases
import com.example.animeapp.util.Constants.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val useCases: UseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedHero: MutableState<Hero?> = mutableStateOf(null)
    val selectedHero = _selectedHero


    init {
        viewModelScope.launch {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)

            heroId?.let {
                _selectedHero.value = useCases.getSelectedHeroUseCase(heroId = it)
            }
        }
    }
}