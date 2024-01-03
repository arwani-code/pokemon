package com.arwani.pokemon.ui.screen.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arwani.pokemon.ui.theme.Red20
import com.arwani.pokemon.ui.theme.White200


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    canNavigate: Boolean = false,
    navigateUp: () -> Unit = {},
    actionsRow: @Composable RowScope.() -> Unit = {},
    titleColor: Color = Color.Black,
    scrollBehavior: TopAppBarScrollBehavior,
    canScroll: Boolean = false
) {
    when {
        canNavigate -> {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                modifier = modifier,
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (canScroll) MaterialTheme.colorScheme.background else Red20
                ),
                actions = actionsRow
            )
        }


        else -> {
            TopAppBar(
                title = { Text(text = title, color = titleColor) },
                modifier = modifier,
                actions = actionsRow,
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (canScroll) White200 else MaterialTheme.colorScheme.background,
                    scrolledContainerColor = White200
                )
            )
        }
    }

}