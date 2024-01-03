package com.arwani.pokemon.domain.repository

import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.data.helper.SortType
import com.arwani.pokemon.data.source.remote.network.ApiResponse
import com.arwani.pokemon.data.source.remote.response.PokemonDetailResponse
import com.arwani.pokemon.domain.model.Pokemon
import com.arwani.pokemon.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    fun getPokemon(sortType: SortType): Flow<UiResult<List<Pokemon>>>

    fun getPokemonDetail(id: Int): Flow<UiResult<List<PokemonDetail>>>

    fun updateNamePokemon(data: PokemonDetail, catch: Int)
}