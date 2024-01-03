package com.arwani.pokemon.ui.screen.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    canNavigate: Boolean = false,
    navigateUp: () -> Unit = {},
    actionsRow: @Composable RowScope.() -> Unit = {},
    titleColor: Color = Color.Black,
) {
    when {
        canNavigate -> {
            TopAppBar(
                title = { Text(text = title, color = titleColor) },
                modifier = modifier,
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = titleColor
                        )
                    }
                },
            )
        }


        else -> {
            TopAppBar(
                title = { Text(text = title, color = titleColor) },
                modifier = modifier,
                actions = actionsRow
            )
        }
    }

}