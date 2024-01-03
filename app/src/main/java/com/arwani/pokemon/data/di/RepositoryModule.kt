package com.arwani.pokemon.data.di

import com.arwani.pokemon.BuildConfig
import com.arwani.pokemon.data.PokemonRepository
import com.arwani.pokemon.data.source.remote.network.ApiService
import com.arwani.pokemon.domain.repository.IPokemonRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(pokemonRepository: PokemonRepository): IPokemonRepository

}