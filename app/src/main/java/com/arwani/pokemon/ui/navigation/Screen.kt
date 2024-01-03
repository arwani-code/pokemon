package com.arwani.pokemon.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("home/{pokemon}") {
        fun createRoute(pokemon: Int) = "home/$pokemon"
    }
}