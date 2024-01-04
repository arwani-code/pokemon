package com.arwani.pokemon.ui.screen.mypokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.arwani.pokemon.R
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
//    val myPokemon by viewModel.myPokemon.collectAsState(initial = emptyList())
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
        viewModel.myPokemon.collectAsState(initial = emptyList()).value.let { myPokemon ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                if (myPokemon.isEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingIndicator()
                        }
                    }
                }
                items(myPokemon, key = { it.id }) { pokemon ->
                    CardItem(
                        id = pokemon.id,
                        title = "${pokemon.name}-${pokemon.catch}",
                        imageUrl = pokemon.sprites,
                        navigateItem = navigateToDetail
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.infinite_loader))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        modifier = modifier.size(500.dp),
        composition = composition,
        progress = { progress },
        alignment = Alignment.Center
    )
}