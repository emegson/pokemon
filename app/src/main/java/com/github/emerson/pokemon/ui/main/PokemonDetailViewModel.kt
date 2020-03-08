package com.github.emerson.pokemon.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.emerson.pokemon.data.model.Pokemon

class PokemonDetailViewModel : ViewModel() {

    val pokemon = MutableLiveData<Pokemon>()

    fun fetch(monster: Pokemon){
        pokemon.postValue(monster)
    }
}
