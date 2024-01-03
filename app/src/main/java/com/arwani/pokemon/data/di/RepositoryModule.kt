package com.arwani.pokemon.data.di

import com.arwani.pokemon.data.PokemonRepository
import com.arwani.pokemon.domain.repository.IPokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(pokemonRepository: PokemonRepository): IPokemonRepository

}