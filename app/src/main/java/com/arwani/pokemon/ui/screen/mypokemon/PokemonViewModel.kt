package com.arwani.pokemon.ui.screen.mypokemon

import androidx.lifecycle.ViewModel
import com.arwani.pokemon.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(useCase: PokemonUseCase) : ViewModel() {

    val myPokemon = useCase.getMyPokemon()

}