package com.arwani.pokemon.ui.screen.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.arwani.pokemon.domain.model.PokemonDetail
import com.arwani.pokemon.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: PokemonUseCase
) : ViewModel() {

    var pokedexId by mutableStateOf("")
    var fab by mutableStateOf(false)
    var getCatch by mutableStateOf(false)

    fun getPokemonDetail(id: Int) = useCase.getPokemonDetail(id)

    fun addNumberCatch(pokemon: PokemonDetail?, catch: Int) {
        if (pokemon != null) {
            useCase.updateNamePokemon(pokemon, calculatePokemonCatch(catch), catch)
//            catch += 1
        }
    }

    private fun calculatePokemonCatch(n: Int): Int =
        if (n <= 1) n
        else calculatePokemonCatch(n - 1) + calculatePokemonCatch(n - 2)

}