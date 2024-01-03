package com.arwani.pokemon.data.source.remote.network

import com.arwani.pokemon.BuildConfig
import com.arwani.pokemon.data.source.remote.response.PokemonDetailResponse
import com.arwani.pokemon.data.source.remote.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getDetailPokemon(
        @Path("id") id: Int
    ): PokemonDetailResponse
}