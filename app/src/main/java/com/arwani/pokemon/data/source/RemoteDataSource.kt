package com.arwani.pokemon.data.source

import android.util.Log
import com.arwani.pokemon.data.source.remote.network.ApiResponse
import com.arwani.pokemon.data.source.remote.network.ApiService
import com.arwani.pokemon.data.source.remote.response.PokemonDetailResponse
import com.arwani.pokemon.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPokemon(): Flow<ApiResponse<PokemonResponse>> = flow {
        try {
            val response = apiService.getPokemon()
            if (response.results.isNotEmpty()) emit(ApiResponse.Success(response))
            else emit(ApiResponse.Empty)
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun getDetailPokemon(id: Int): Flow<ApiResponse<PokemonDetailResponse>> = flow {
        try {
            val response = apiService.getDetailPokemon(id)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}