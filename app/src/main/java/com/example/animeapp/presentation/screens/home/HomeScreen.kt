package com.example.animeapp.presentation.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.animeapp.presentation.common.ListContent

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val allheroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    Scaffold(topBar = {
        HomeTopBar()
    }, content = {
        ListContent(
            heroes = allheroes,
            navHostController = navHostController
        )
    })
}