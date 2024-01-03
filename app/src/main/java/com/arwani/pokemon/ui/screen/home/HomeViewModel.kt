package com.arwani.pokemon.ui.screen.home

import androidx.lifecycle.ViewModel
import com.arwani.pokemon.data.helper.SortType
import com.arwani.pokemon.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(pokemonUseCase: PokemonUseCase) : ViewModel() {

    private val _sort: MutableStateFlow<SortType> = MutableStateFlow(SortType.RANDOM)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemon = _sort.flatMapLatest { pokemonUseCase.getPokemon(it) }

    fun changeSortType(type: String) {
        _sort.value = when (type) {
            "A-Z" -> SortType.ASCENDING
            "Z-A" -> SortType.DESCENDING
            else -> SortType.RANDOM
        }
    }
}