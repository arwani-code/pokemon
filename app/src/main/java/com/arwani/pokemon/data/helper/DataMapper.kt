package com.arwani.pokemon.data.helper

import com.arwani.pokemon.data.source.local.entity.DetailPokemonEntity
import com.arwani.pokemon.data.source.local.entity.PokemonEntity
import com.arwani.pokemon.data.source.local.entity.StatName
import com.arwani.pokemon.data.source.remote.response.PokemonDetailResponse
import com.arwani.pokemon.data.source.remote.response.PokemonResponse
import com.arwani.pokemon.data.utils.getHeightString
import com.arwani.pokemon.data.utils.getId
import com.arwani.pokemon.data.utils.getIdString
import com.arwani.pokemon.data.utils.getImageUrl
import com.arwani.pokemon.data.utils.getWeightString
import com.arwani.pokemon.domain.model.Pokemon
import com.arwani.pokemon.domain.model.PokemonDetail

object DataMapper {

    fun mapResponsesToEntities(input: PokemonResponse): List<PokemonEntity> =
        input.results.filter { item -> item.url.isNotEmpty() }.map {
            PokemonEntity(
                id = it.url.getId(),
                imageUrl = it.url.getImageUrl(),
                name = it.name
            )
        }

    fun mapEntitiesToDomain(input: List<PokemonEntity>): List<Pokemon> =
        input.map {
            Pokemon(
                id = it.id,
                imageUrl = it.imageUrl,
                name = it.name
            )
        }

    fun mapResponsesToEntitiesDetail(input: PokemonDetailResponse): List<DetailPokemonEntity> {
        val data = ArrayList<DetailPokemonEntity>()
        data.add(
            DetailPokemonEntity(
                id = input.id,
                height = input.height.getHeightString(),
                weight = input.weight.getWeightString(),
                name = input.name,
                abilities = input.abilities.filter { it.ability.name.isNotEmpty() }
                    .map { it.ability.name },
                order = input.order,
                moves = input.moves.filter { it.move.name.isNotEmpty() }
                    .map { item -> item.move.name },
                sprites = input.id.getImageUrl(),
                stats = input.stats.filter { it.stat.name.isNotEmpty() }
                    .map { StatName(it.baseStat, it.stat.name) },
                types = input.types.map { it.type.name },
                pokedexId = input.id.getIdString()
            )
        )
        return data
    }

    fun mapEntitiesToDomainDetail(inputData: List<DetailPokemonEntity>): List<PokemonDetail> {
        return inputData.map { input ->
            PokemonDetail(
                id = input.id,
                types = input.types,
                stats = input.stats,
                sprites = input.sprites,
                order = input.order,
                moves = input.moves,
                abilities = input.abilities,
                name = input.name,
                weight = input.weight,
                height = input.height,
                pokedexId = input.pokedexId,
                catch =  input.catch,
                countCatch = input.countCatch
            )
        }
    }

    fun mapDomainToEntity(input: PokemonDetail): DetailPokemonEntity =
        DetailPokemonEntity(
            id = input.id,
            pokedexId = input.pokedexId,
            height = input.height,
            weight = input.weight,
            name = input.name,
            abilities = input.abilities,
            moves = input.moves,
            order = input.order,
            sprites = input.sprites,
            stats = input.stats,
            types = input.types,
            catch = input.catch
        )

    fun mapEntitiesToDomainMyPokemon(inputData: List<DetailPokemonEntity>) : List<Pokemon> =
        inputData.map {
            Pokemon(
                id = it.id.toString(),
                name = it.name,
                imageUrl = it.sprites,
                catch = it.catch
            )
        }
}