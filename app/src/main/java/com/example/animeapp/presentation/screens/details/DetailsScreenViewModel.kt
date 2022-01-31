package com.example.animeapp.presentation.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.model.Hero
import com.example.animeapp.domain.use_cases.UseCases
import com.example.animeapp.util.Constants.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val useCases: UseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedHero: MutableStateFlow<Hero?> = MutableStateFlow(null)
    val selectedHero = _selectedHero

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)

            heroId?.let {
                _selectedHero.value = useCases.getSelectedHeroUseCase(heroId = it)
            }
        }
    }

    fun generateColorPalette() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GenerateColorPalette)
        }
    }

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }
}

sealed class UiEvent {
    object GenerateColorPalette : UiEvent()
}