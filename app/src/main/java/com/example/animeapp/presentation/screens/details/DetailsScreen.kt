package com.example.animeapp.presentation.screens.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
) {

    val selectedHero by detailsScreenViewModel.selectedHero.collectAsState()

    DetailsContent(
        navHostController = navHostController,
        selectedHero = selectedHero
    )
}