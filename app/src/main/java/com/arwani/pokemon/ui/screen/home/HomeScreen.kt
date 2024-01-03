package com.arwani.pokemon.ui.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.ui.screen.components.DropDownMenuItems
import com.arwani.pokemon.ui.screen.components.PokemonTopAppBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    lazyListState: LazyGridState = rememberLazyGridState()
) {

    val pokemon by viewModel.pokemon.collectAsState(initial = UiResult.Loading())

    Scaffold(
        topBar = {
            PokemonTopAppBar(
                title = "Home",
                actionsRow = {
                    IconButton(onClick = { /*TODO*/ }) {
                        DropDownMenuItems(isUpdateItem = {
                            viewModel.changeSortType(it)
                        })
                    }
                }

            )
        }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (pokemon) {
                is UiResult.Loading -> {
                    Column(
                        modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LinearProgressIndicator(progress = (49 / 49).toFloat())
//                        CircularProgressIndicator()
                    }
                }

                is UiResult.Success -> {
                    LazyColumn {
                        if (pokemon != null) {
                            items(pokemon.data!!) {
                                Text(
                                    text = it.name,
                                    modifier = modifier.clickable { navigateToDetail(it.id.toInt()) })
                            }
                        }
                    }
                }

                is UiResult.Error -> {}
            }
        }

    }
}