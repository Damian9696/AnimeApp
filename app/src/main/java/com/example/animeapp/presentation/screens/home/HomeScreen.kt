package com.example.animeapp.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animeapp.presentation.components.RatingWidget
import com.example.animeapp.ui.theme.LARGE_PADDING

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val allheroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    Scaffold(topBar = {
        HomeTopBar()
    }) {
        RatingWidget(
            modifier = Modifier.padding(all = LARGE_PADDING),
            rating = 0.3
        )
    }
}