package com.example.animeapp.presentation.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.animeapp.domain.model.OnBoardingPage
import com.example.animeapp.ui.theme.welcomeScreenBackgroundColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

val onBoardingPages = listOf(
    OnBoardingPage.First,
    OnBoardingPage.Second,
    OnBoardingPage.Third,
)

@ExperimentalPagerApi
@Composable
fun WelcomeScreen(navHostController: NavHostController) {

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.welcomeScreenBackgroundColor)
    ) {
        HorizontalPager(
            count = onBoardingPages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { pageIndex ->
            PagerScreen(onBoardingPage = onBoardingPages[pageIndex])
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {

}