package com.arwani.pokemon.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arwani.pokemon.data.source.local.entity.PokemonEntity


@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}