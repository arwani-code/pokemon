package com.arwani.pokemon.data.di

import android.content.Context
import androidx.room.Room
import com.arwani.pokemon.data.source.local.room.PokemonDao
import com.arwani.pokemon.data.source.local.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase =
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().build()


    @Provides
    fun provideMovieDao(database: PokemonDatabase): PokemonDao = database.pokemonDao()
}