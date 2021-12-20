package com.example.animeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.animeapp.presentation.screens.splash.SplashScreen
import com.example.animeapp.util.Constants.DETAILS_ARGUMENT_KEY

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navHostController = navController)
        }
        composable(route = Screen.Welcome.route) {
            //composable view
        }
        composable(route = Screen.Home.route) {
            //composable view
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            //composable view
        }
        composable(route = Screen.Search.route) {
            //composable view
        }
    }
}