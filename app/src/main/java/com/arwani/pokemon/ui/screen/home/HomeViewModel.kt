package com.arwani.pokemon.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arwani.pokemon.data.helper.SortType
import com.arwani.pokemon.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(pokemonUseCase: PokemonUseCase) : ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading.asStateFlow()

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

    init {
        viewModelScope.launch {
            _loading.value = true
            delay(7000L)
            _loading.value = false
        }
    }
}