package com.example.animeapp.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
) {

    val selectedHero by detailsScreenViewModel.selectedHero

}