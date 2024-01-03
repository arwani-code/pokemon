package com.arwani.pokemon.domain.model

import androidx.compose.runtime.Immutable
import com.arwani.pokemon.data.source.local.entity.StatName

@Immutable
data class PokemonDetail(
    val id: Int,
    val abilities: List<String>,
    val height: String,
    val moves: List<String>,
    val name: String,
    val order: Int,
    val sprites: String,
    val stats: List<StatName>,
    val types: List<String>,
    val weight: String
)
