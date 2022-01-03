package com.example.animeapp.presentation.screens.details

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.animeapp.BuildConfig
import com.example.animeapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.example.animeapp.util.PaletteGenerator.extractColorsFromBitmap
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val selectedHero by detailsScreenViewModel.selectedHero.collectAsState()
    val colorPalette by detailsScreenViewModel.colorPalette


    if (colorPalette.isNotEmpty()) {
        DetailsContent(
            navHostController = navHostController,
            selectedHero = selectedHero,
            colors = colorPalette
        )
    } else {
        detailsScreenViewModel.generateColorPalette()
    }

    LaunchedEffect(key1 = true) {
        detailsScreenViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                    val bitmap = convertImageUrlToBitmap(
                        imageUrl = "${BuildConfig.BASE_URL}${selectedHero?.image}",
                        context = context
                    )
                    bitmap?.let {
                        detailsScreenViewModel.setColorPalette(
                            colors = extractColorsFromBitmap(bitmap = it)
                        )
                    }
                }
            }
        }
    }

}