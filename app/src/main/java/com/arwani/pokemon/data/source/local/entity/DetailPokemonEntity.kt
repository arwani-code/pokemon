package com.arwani.pokemon.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arwani.pokemon.data.source.remote.response.Sprites


@Entity(tableName = "detail_pokemon")
data class DetailPokemonEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "abilities")
    val abilities: List<String>,
    @ColumnInfo(name = "height")
    val height: String,
    @ColumnInfo(name = "moves")
    val moves: List<String>,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "order")
    val order: Int,
    @ColumnInfo(name = "sprites")
    val sprites: String,
    @ColumnInfo(name = "stats")
    val stats: List<StatName>,
    @ColumnInfo(name = "types")
    val types: List<String>,
    @ColumnInfo(name = "weight")
    val weight: String
)


data class StatName(
    val baseStat: Int,
    val stat: String
)

