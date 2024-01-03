package com.arwani.pokemon.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arwani.pokemon.data.helper.PokemonDataConverters
import com.arwani.pokemon.data.source.local.entity.DetailPokemonEntity
import com.arwani.pokemon.data.source.local.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class, DetailPokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [PokemonDataConverters::class])
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}