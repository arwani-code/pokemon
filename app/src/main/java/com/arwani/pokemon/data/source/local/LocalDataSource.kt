package com.arwani.pokemon.data.source.local

import com.arwani.pokemon.data.helper.SortType
import com.arwani.pokemon.data.helper.SortUtils
import com.arwani.pokemon.data.source.local.entity.DetailPokemonEntity
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

    fun getDetailPokemon(id: Int): Flow<List<DetailPokemonEntity>> = pokemonDao.getDetailPokemon(id)

    fun getMyPokemon(): Flow<List<DetailPokemonEntity>> = pokemonDao.getMyPokemon()

    suspend fun insertPokemon(data: List<PokemonEntity>) = pokemonDao.insertPokemon(data)

    suspend fun insertDetailPokemon(data: List<DetailPokemonEntity>) =
        pokemonDao.insertDetailPokemon(data.first())

    fun updateNamePokemon(data: DetailPokemonEntity, catch: Int, countCatch: Int) {
        data.catch = catch.toString()
        data.countCatch = countCatch
        pokemonDao.updateNamePokemon(data)
    }
}