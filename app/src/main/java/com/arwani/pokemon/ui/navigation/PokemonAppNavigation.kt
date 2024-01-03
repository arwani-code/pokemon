package com.arwani.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arwani.pokemon.ui.screen.detail.DetailScreen
import com.arwani.pokemon.ui.screen.home.HomeScreen

@Composable
fun PokemonAppNavigation(
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navigateToDetail = {
                navController.navigate(Screen.Detail.createRoute(it))
            })
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("pokemon") { type = NavType.IntType })
        ) {
            val id = it?.arguments?.getInt("pokemon") ?: 1
            DetailScreen(id = id, navigateUp = { navController.navigateUp() })
        }
    }

}