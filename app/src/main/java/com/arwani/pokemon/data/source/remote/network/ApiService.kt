package com.arwani.pokemon.data.source.remote.network

import com.arwani.pokemon.BuildConfig
import com.arwani.pokemon.data.source.remote.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(): PokemonResponse

}