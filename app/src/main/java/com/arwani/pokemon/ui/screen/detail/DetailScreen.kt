package com.arwani.pokemon.ui.screen.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.ui.screen.components.DropDownMenuItems
import com.arwani.pokemon.ui.screen.components.PokemonTopAppBar

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: Int,
    navigateUp: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    var catch by remember {
        mutableStateOf(false)
    }
    val pokemon by viewModel.getPokemonDetail(id).collectAsState(initial = UiResult.Loading())
    LaunchedEffect(key1 = catch, block = {
        if (catch) {
            viewModel.addNumberCatch()
            Toast.makeText(context, "catch....", Toast.LENGTH_LONG).show()
        }
    })
    Scaffold(
        topBar = {
            PokemonTopAppBar(
                title = "Detail",
                canNavigate = true,
                navigateUp = navigateUp
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
                    Column(
                        modifier = modifier.fillMaxSize()
                    ) {
                        Text(text = pokemon.data?.first()?.name.toString())
                        Text(text = pokemon.data?.first()?.height.toString())
                        Text(text = pokemon.data?.first()?.weight.toString())
                        Text(text = pokemon.data?.first()?.abilities.toString())
                        Button(onClick = { catch = !catch }) {
                            if (catch) Text(text = "Release")
                            else Text(text = "Catch")
                        }
                    }
                }

                is UiResult.Error -> {}
            }
        }

    }

}