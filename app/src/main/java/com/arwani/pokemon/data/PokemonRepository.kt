package com.arwani.pokemon.data

import com.arwani.pokemon.data.helper.DataMapper
import com.arwani.pokemon.data.helper.NetworkBoundUiResult
import com.arwani.pokemon.data.helper.SortType
import com.arwani.pokemon.data.source.local.LocalDataSource
import com.arwani.pokemon.data.source.local.entity.DetailPokemonEntity
import com.arwani.pokemon.data.source.remote.RemoteDataSource
import com.arwani.pokemon.data.source.remote.network.ApiResponse
import com.arwani.pokemon.data.source.remote.response.PokemonDetailResponse
import com.arwani.pokemon.data.source.remote.response.PokemonResponse
import com.arwani.pokemon.data.utils.AppExecutors
import com.arwani.pokemon.domain.model.Pokemon
import com.arwani.pokemon.domain.model.PokemonDetail
import com.arwani.pokemon.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PokemonRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IPokemonRepository {

    override fun getPokemon(sortType: SortType): Flow<UiResult<List<Pokemon>>> =
        object : NetworkBoundUiResult<List<Pokemon>, PokemonResponse>() {
            override fun loadFromDB(): Flow<List<Pokemon>> =
                localDataSource.getPokemon(sortType).map {
                    DataMapper.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Pokemon>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<PokemonResponse>> =
                remoteDataSource.getPokemon()

            override suspend fun saveCallResult(data: PokemonResponse) {
                val input = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPokemon(input)
            }
        }.asFlow()

    override fun getPokemonDetail(id: Int): Flow<UiResult<List<PokemonDetail>>> =
        object : NetworkBoundUiResult<List<PokemonDetail>, PokemonDetailResponse>() {
            override fun loadFromDB(): Flow<List<PokemonDetail>> =
                localDataSource.getDetailPokemon(id).map {
                    DataMapper.mapEntitiesToDomainDetail(it)
                }

            override fun shouldFetch(data: List<PokemonDetail>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<PokemonDetailResponse>> =
                remoteDataSource.getDetailPokemon(id)

            override suspend fun saveCallResult(data: PokemonDetailResponse) {
                val input = DataMapper.mapResponsesToEntitiesDetail(data)
                localDataSource.insertDetailPokemon(input)
            }
        }.asFlow()

    override fun updateNamePokemon(data: PokemonDetail, catch: Int, countCatch: Int) {
        val pokemonDetail = DataMapper.mapDomainToEntity(data)
        appExecutors.diskIO().execute {
            localDataSource.updateNamePokemon(pokemonDetail, catch, countCatch = countCatch)
        }
    }

    override fun getMyPokemon(): Flow<List<DetailPokemonEntity>> = localDataSource.getMyPokemon()
}