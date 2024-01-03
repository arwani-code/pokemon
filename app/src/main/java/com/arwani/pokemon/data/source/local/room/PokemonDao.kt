package com.arwani.pokemon.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.arwani.pokemon.data.source.local.entity.DetailPokemonEntity
import com.arwani.pokemon.data.source.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @RawQuery(observedEntities = [PokemonEntity::class])
    fun getPokemon(query: SupportSQLiteQuery): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM detail_pokemon where id = :pokeId")
    fun getDetailPokemon(pokeId: Int): Flow<List<DetailPokemonEntity>>

    @Query("SELECT * FROM detail_pokemon WHERE catch IS NOT NULL AND catch <> ''")
    fun getMyPokemon(): Flow<List<DetailPokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(data: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailPokemon(data: DetailPokemonEntity)

    @Update
    fun updateNamePokemon(data: DetailPokemonEntity)


}