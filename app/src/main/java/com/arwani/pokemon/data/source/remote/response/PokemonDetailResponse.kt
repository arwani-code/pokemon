package com.arwani.pokemon.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("abilities")
    val abilities: List<Ability>,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("moves")
    val moves: List<Move>,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("stats")
    val stats: List<Stat>,
    @SerializedName("types")
    val types: List<Type>,
    @SerializedName("weight")
    val weight: Int
)


data class Ability(
    @SerializedName("ability")
    val ability: AbilityX
)

data class AbilityX(
    @SerializedName("name")
    val name: String
)

data class Move(
    @SerializedName("move")
    val move: MoveX
)

data class MoveX(
    @SerializedName("name")
    val name: String
)

data class Sprites(
    @SerializedName("back_default")
    val backDefault: String
)

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("stat")
    val stat: StatX
)

data class StatX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Type(
    @SerializedName("type")
    val type: TypeX
)

data class TypeX(
    @SerializedName("name")
    val name: String
)