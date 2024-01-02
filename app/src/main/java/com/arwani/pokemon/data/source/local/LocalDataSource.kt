package com.arwani.pokemon.data.source.local

import com.arwani.pokemon.data.helper.SortType
import com.arwani.pokemon.data.helper.SortUtils
import com.arwani.pokemon.data.source.local.entity.PokemonEntity
import com.arwani.pokemon.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalDataSource @Inject constructor(private val pokemonDao: PokemonDao) {

    fun getPokemon(sortType: SortType): Flow<List<PokemonEntity>> {
        val query = SortUtils.getSortedQuery(sortType)
        return pokemonDao.getPokemon(query)
    }

}