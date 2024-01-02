package com.arwani.pokemon.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("results")
    val results: List<Result>
)