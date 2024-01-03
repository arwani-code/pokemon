package com.arwani.pokemon.ui.screen.detail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.ui.screen.components.PokemonTopAppBar
import com.arwani.pokemon.ui.theme.Green20
import com.arwani.pokemon.ui.theme.Red20
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: Int,
    navigateUp: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    topBarScrollBehavior: TopAppBarScrollBehavior,
    systemUiController: SystemUiController,
) {
    val state: ScrollState = rememberScrollState()
    LaunchedEffect(key1 = state.canScrollBackward, block = {
        if (state.canScrollBackward) systemUiController.setStatusBarColor(Color.White)
        else systemUiController.setStatusBarColor(Red20)
    })
//    var catch by rememberSaveable {
//        mutableStateOf(false)
//    }

    val snackbarHostState = remember { SnackbarHostState() }
    val pokemon by viewModel.getPokemonDetail(id).collectAsState(initial = UiResult.Loading())

    Scaffold(
        topBar = {
            PokemonTopAppBar(
                title = "Pokedex",
                canNavigate = true,
                navigateUp = navigateUp,
                scrollBehavior = topBarScrollBehavior,
                actionsRow = {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = viewModel.pokedexId,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                },
                canScroll = state.canScrollBackward
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    if (viewModel.getCatch) Text(text = "release")
                    else Text(text = "catch")
                },
                icon = {
                    if (viewModel.getCatch) Icon(imageVector = Icons.Rounded.Star, contentDescription = "")
                    else Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = "")
                },
                onClick = {
                    viewModel.getCatch = !viewModel.getCatch
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                    val pokemonDetail = pokemon.data?.first()
                    if (pokemonDetail != null) {
                        var countCatch by remember {
                            mutableIntStateOf(pokemonDetail.countCatch)
                        }
                        LaunchedEffect(key1 = viewModel.getCatch, block = {
                            viewModel.pokedexId = pokemonDetail.pokedexId
                            Log.i("SKNNCJ", "DetailScreen: $countCatch")
                            if (viewModel.getCatch) {
                                viewModel.addNumberCatch(pokemonDetail, countCatch)
                                countCatch += 1
                                snackbarHostState.showSnackbar(message = "Catch...")
                            }
                        })
                    }
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(state)
                    ) {
                        Box(
                            modifier = modifier
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = 32.dp,
                                        bottomEnd = 32.dp
                                    )
                                )
                                .height(200.dp)
                                .fillMaxWidth()
                                .background(Red20),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(pokemonDetail?.sprites)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = pokemonDetail?.sprites.toString(),
                                contentScale = ContentScale.FillBounds,
                                modifier = modifier
                                    .size(170.dp)
                            )
                        }
                        Column(
                            modifier = modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (pokemonDetail?.catch?.isEmpty() == true) {
                                Text(
                                    text = pokemonDetail?.name.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 48.sp
                                )
                            } else {
                                Text(
                                    text = "${pokemonDetail?.name}-${pokemonDetail?.catch}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 48.sp
                                )
                            }
                            FlowRow(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.Center,
                                maxItemsInEachRow = pokemonDetail?.stats?.size ?: 1
                            ) {
                                val itemModifier = Modifier
                                    .padding(8.dp)
                                    .height(30.dp)
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Green20)
                                pokemonDetail?.types?.forEach { name ->
                                    Box(
                                        modifier = itemModifier,
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = name,
                                            fontSize = 16.sp,
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = modifier
                                    .padding(horizontal = 24.dp, vertical = 16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(
                                    modifier = modifier,
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = pokemonDetail?.weight.toString(),
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = modifier.height(8.dp))
                                    Text(
                                        text = "Weigth",
                                        fontSize = 16.sp,
                                    )
                                }
                                Column(
                                    modifier = modifier,
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = pokemonDetail?.height.toString(),
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = modifier.height(8.dp))
                                    Text(
                                        text = "Height",
                                        fontSize = 16.sp,
                                    )
                                }
                            }
                            Column(
                                modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray.copy(0.1f)),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Moves",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 12.dp
                                    )
                                )
                                FlowRow(
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 18.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    maxItemsInEachRow = pokemonDetail?.stats?.size ?: 1
                                ) {
                                    pokemonDetail?.moves?.forEach { name ->
                                        Box(
                                            modifier = modifier.padding(horizontal = 4.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "$name, ",
                                                fontSize = 16.sp,
                                            )
                                        }
                                    }
                                }
                            }
                        }
//                        if (pokemonDetail?.catch?.isEmpty() == true) Text(text = pokemonDetail.name)
//                        else Text(text = "${pokemonDetail?.name}-${pokemonDetail?.catch}")
//                        Text(text = pokemonDetail?.height.toString())
//                        Text(text = pokemonDetail?.weight.toString())
//                        Text(text = pokemonDetail?.abilities.toString())
//                        Button(onClick = { catch = !catch }) {
//                            if (catch) Text(text = "Release")
//                            else Text(text = "Catch")
//                        }
                    }
                }

                is UiResult.Error -> {}
            }
        }

    }

}