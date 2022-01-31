package com.example.animeapp.presentation.screens.search

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.animeapp.presentation.common.ListContent
import com.example.animeapp.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )

    val searchQuery by searchViewModel.searchQuery
    val searchedHeroes = searchViewModel.searchedHeroes.collectAsLazyPagingItems()

    Scaffold(topBar = {
        SearchTopBar(
            text = searchQuery,
            onTextChange = {
                searchViewModel.updateSearchQuery(it)
            },
            onSearchClicked = {
                searchViewModel.searchHeroes(query = it)
            },
            onCloseClicked = {
                navHostController.popBackStack()
            }
        )
    }) {
        ListContent(
            heroes = searchedHeroes,
            navHostController = navHostController
        )
    }
}