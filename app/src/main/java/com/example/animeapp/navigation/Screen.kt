package com.example.animeapp.navigation

import com.example.animeapp.navigation.util.Constants.DETAILS_ARGUMENT_KEY

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Welcome : Screen("welcome_screen")
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
    object Details : Screen("details_screen/{$DETAILS_ARGUMENT_KEY}") {
        fun passHeroId(heroId: Int) = "details_screen/$heroId"
    }

}
