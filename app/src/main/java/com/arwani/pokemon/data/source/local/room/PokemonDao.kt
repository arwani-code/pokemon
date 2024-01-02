package com.arwani.pokemon.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.arwani.pokemon.data.source.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @RawQuery(observedEntities = [PokemonEntity::class])
    fun getPokemon(query: SupportSQLiteQuery): Flow<List<PokemonEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(movie: List<PokemonEntity>)
}