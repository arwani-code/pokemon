package com.arwani.pokemon.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Pokemon(
    val id: String,
    val name: String,
    val imageUrl: String,
    val catch: String = ""
)
