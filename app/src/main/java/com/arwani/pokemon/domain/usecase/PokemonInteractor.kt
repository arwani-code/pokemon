package com.arwani.pokemon.domain.usecase

import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.data.helper.SortType
import com.arwani.pokemon.data.source.local.entity.DetailPokemonEntity
import com.arwani.pokemon.domain.model.Pokemon
import com.arwani.pokemon.domain.model.PokemonDetail
import com.arwani.pokemon.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonInteractor @Inject constructor(
    private val iPokemonRepository: IPokemonRepository
) : PokemonUseCase {

    override fun getPokemon(sortType: SortType): Flow<UiResult<List<Pokemon>>> =
        iPokemonRepository.getPokemon(sortType)

    override fun getPokemonDetail(id: Int): Flow<UiResult<List<PokemonDetail>>> =
        iPokemonRepository.getPokemonDetail(id)

    override fun updateNamePokemon(data: PokemonDetail, catch: Int, countCatch: Int) {
        iPokemonRepository.updateNamePokemon(data, catch, countCatch)
    }

    override fun getMyPokemon(): Flow<List<DetailPokemonEntity>> =
        iPokemonRepository.getMyPokemon()
}