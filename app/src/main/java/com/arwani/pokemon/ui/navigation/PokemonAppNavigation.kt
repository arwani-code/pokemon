package com.arwani.pokemon.ui.navigation

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arwani.pokemon.ui.screen.detail.DetailScreen
import com.arwani.pokemon.ui.screen.home.HomeScreen
import com.arwani.pokemon.ui.screen.mypokemon.PokemonScreen
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonAppNavigation(
    navController: NavHostController = rememberNavController(),
    topBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    uiSystemController: SystemUiController = rememberSystemUiController(),
    lazyGridState: LazyGridState = rememberLazyGridState(),
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                nestedScrollConnection = topBarScrollBehavior.nestedScrollConnection,
                topBarScrollBehavior = topBarScrollBehavior,
                navigateToDetail = {
                    navController.navigate(Screen.Detail.createRoute(it))
                },
                lazyGridState = lazyGridState,
                navController = navController,
                systemUiController = uiSystemController
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("pokemon") { type = NavType.IntType })
        ) {
            val id = it?.arguments?.getInt("pokemon") ?: 1
            DetailScreen(
                topBarScrollBehavior = topBarScrollBehavior,
                id = id, navigateUp = { navController.navigateUp() },
                systemUiController = uiSystemController
            )
        }

        composable(route = Screen.MyPokemon.route) {
            PokemonScreen(
                topBarScrollBehavior = topBarScrollBehavior,
                navController = navController,
                navigateToDetail = {
                    navController.navigate(Screen.Detail.createRoute(it))
                }
            )
        }
    }

}