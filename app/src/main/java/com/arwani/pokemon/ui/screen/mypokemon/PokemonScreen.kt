package com.arwani.pokemon.ui.screen.mypokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.arwani.pokemon.ui.navigation.Screen
import com.arwani.pokemon.ui.screen.components.BottomBar
import com.arwani.pokemon.ui.screen.components.CardItem
import com.arwani.pokemon.ui.screen.components.PokemonTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier,
    topBarScrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    navigateToDetail: (Int) -> Unit,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val myPokemon by viewModel.myPokemon.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            PokemonTopAppBar(
                title = "My Pokemon List",
                scrollBehavior = topBarScrollBehavior,
                canScroll = false
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentScreen = Screen.MyPokemon.route
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(myPokemon, key = { it.id }) {
                CardItem(
                    id = it.id,
                    title = "${it.name}-${it.catch}",
                    imageUrl = it.sprites,
                    navigateItem = navigateToDetail
                )
            }
        }
    }
}