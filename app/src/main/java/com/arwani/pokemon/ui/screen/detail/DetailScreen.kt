package com.arwani.pokemon.ui.screen.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.data.utils.parseStatToAbbr
import com.arwani.pokemon.data.utils.parseStatToColor
import com.arwani.pokemon.domain.model.PokemonDetail
import com.arwani.pokemon.ui.screen.components.MyLinearProgressIndicator
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
            if (viewModel.fab) ExtendedFloatingActionButton(
                text = {
                    if (viewModel.getCatch) Text(text = "release")
                    else Text(text = "catch")
                },
                icon = {
                    if (viewModel.getCatch) Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = ""
                    )
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
                        ContentLoading()
                    }
                }

                is UiResult.Success -> {
                    val pokemonDetail = pokemon.data?.first()
                    viewModel.fab = true
                    if (pokemonDetail != null) {
                        var countCatch by remember {
                            mutableIntStateOf(pokemonDetail.countCatch)
                        }
                        LaunchedEffect(key1 = viewModel.getCatch, block = {
                            viewModel.pokedexId = pokemonDetail.pokedexId
                            if (viewModel.getCatch) {
                                viewModel.addNumberCatch(pokemonDetail, countCatch)
                                countCatch += 1
                                snackbarHostState.showSnackbar(message = "Catch...")
                            }
                        })

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
                                        .data(pokemonDetail.sprites)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = pokemonDetail.sprites,
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
                                if (pokemonDetail.catch.isEmpty()) {
                                    Text(
                                        text = pokemonDetail.name.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 48.sp
                                    )
                                } else {
                                    Text(
                                        text = "${pokemonDetail.name}-${pokemonDetail.catch}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 48.sp
                                    )
                                }
                                FlowRow(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    maxItemsInEachRow = pokemonDetail.stats.size
                                ) {
                                    val itemModifier = Modifier
                                        .padding(8.dp)
                                        .height(30.dp)
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Green20)
                                    pokemonDetail.types.forEach { name ->
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
                                            text = pokemonDetail.weight,
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
                                            text = pokemonDetail.height,
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
                                PokemonBaseStats(pokemonInfo = pokemonDetail)
                                Column(
                                    modifier
                                        .fillMaxWidth()
                                        .background(Color.LightGray.copy(0.1f)),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Moves",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        modifier = modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                    )
                                    FlowRow(
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp, vertical = 18.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        maxItemsInEachRow = pokemonDetail.stats.size
                                    ) {
                                        pokemonDetail.moves.forEach { name ->
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
fun ContentLoading(
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        Box(
            modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            MyLinearProgressIndicator(modifier.fillMaxSize())
        }
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier
                    .width(100.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                MyLinearProgressIndicator(modifier.fillMaxSize())
            }
            Box(
                modifier
                    .width(100.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                MyLinearProgressIndicator(modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        ), label = ""
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = statName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PokemonBaseStats(
    pokemonInfo: PokemonDetail,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Base stats:",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.baseStat,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = i * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}