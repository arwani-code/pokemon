package com.arwani.pokemon.domain.usecase

import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.data.helper.SortType
import com.arwani.pokemon.data.source.remote.network.ApiResponse
import com.arwani.pokemon.data.source.remote.response.PokemonDetailResponse
import com.arwani.pokemon.domain.model.Pokemon
import com.arwani.pokemon.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {

    fun getPokemon(sortType: SortType): Flow<UiResult<List<Pokemon>>>

    fun getPokemonDetail(id: Int): Flow<UiResult<List<PokemonDetail>>>
}