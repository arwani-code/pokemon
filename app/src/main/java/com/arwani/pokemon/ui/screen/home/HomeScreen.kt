package com.arwani.pokemon.ui.screen.home

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.arwani.pokemon.R
import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.ui.navigation.Screen
import com.arwani.pokemon.ui.screen.components.BottomBar
import com.arwani.pokemon.ui.screen.components.CardItem
import com.arwani.pokemon.ui.screen.components.DropDownMenuItems
import com.arwani.pokemon.ui.screen.components.MyLinearProgressIndicator
import com.arwani.pokemon.ui.screen.components.PokemonTopAppBar
import com.arwani.pokemon.ui.theme.White200
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    lazyGridState: LazyGridState,
    nestedScrollConnection: NestedScrollConnection,
    topBarScrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    systemUiController: SystemUiController,
    context: Context = LocalContext.current
) {
    val colorBackground = MaterialTheme.colorScheme.background
    var bottomBarOffsetHeightPx by remember { mutableFloatStateOf(0f) }
    val bottomBarHeight = 50.dp
    var bottomBarHeightPx = with(LocalDensity.current) { bottomBarHeight.roundToPx().toFloat() }
    val nestedScroll = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx + delta
                bottomBarOffsetHeightPx = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val bgColor by animateColorAsState(
        if (lazyGridState.canScrollBackward) White200 else MaterialTheme.colorScheme.background,
        label = "color background"
    )

    DisposableEffect(key1 = lazyGridState.canScrollBackward, effect = {
        if (lazyGridState.canScrollBackward) systemUiController.setStatusBarColor(
            White200
        )
        else systemUiController.setStatusBarColor(colorBackground)
        onDispose { systemUiController.setStatusBarColor(colorBackground) }
    })

    val pokemon by viewModel.pokemon.collectAsState(initial = UiResult.Loading())
    val loading by viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
            PokemonTopAppBar(
                title = "Pokedex",
                scrollBehavior = topBarScrollBehavior,
                actionsRow = {
                    IconButton(onClick = { /*TODO*/ }) {
                        DropDownMenuItems(isUpdateItem = {
                            viewModel.changeSortType(it)
                        })
                    }
                },
                canScroll = lazyGridState.canScrollBackward
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentScreen = Screen.Home.route,
                bottomBarHeight = bottomBarHeight,
                bottomBarOffsetHeightPx = bottomBarOffsetHeightPx
            )
        },
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScroll)
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(bgColor)
        ) {
            when (pokemon) {
                is UiResult.Loading -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        if (loading){
                            item(span = { GridItemSpan(2) }) {
                                Box(
                                    modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LoadingIndicator()
                                }
                            }
                        }
                        items(4) {
                            Box(
                                modifier
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            ) {
                                MyLinearProgressIndicator(modifier.fillMaxSize())
                            }
                        }
                    }
                }

                is UiResult.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        state = lazyGridState,
                        modifier = modifier
                            .fillMaxSize()
                            .nestedScroll(nestedScrollConnection)
                    ) {
                        if (loading){
                            item(span = { GridItemSpan(2) }) {
                                Box(
                                    modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LoadingIndicator()
                                }
                            }
                        }
                        if (pokemon != null) {
                            items(pokemon.data!!, key = { it.id }) {
                                CardItem(
                                    id = it.id.toInt(),
                                    title = it.name,
                                    imageUrl = it.imageUrl,
                                    navigateItem = navigateToDetail
                                )
                            }
                        }
                    }
                }

                is UiResult.Error -> {
                    Toast.makeText(context, "Oops, something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}

@Composable
private fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.refresh_loader))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        modifier = modifier.size(100.dp),
        composition = composition,
        progress = { progress },
        alignment = Alignment.Center
    )
}