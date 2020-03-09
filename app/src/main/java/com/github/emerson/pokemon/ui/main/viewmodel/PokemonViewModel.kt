package com.github.emerson.pokemon.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.emerson.pokemon.data.model.Pokemon
import com.github.emerson.pokemon.data.repository.PokemonRepository
import java.util.logging.Level
import java.util.logging.Logger

class PokemonViewModel : ViewModel() {

    private val pokemonRepository = PokemonRepository(Handler(this))
    private val pokemonList: ArrayList<Pokemon> = ArrayList()

    val pokemonLiveData = MutableLiveData<List<Pokemon>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun refresh(offset: Int) {
        loading.postValue(true)
        loadError.postValue(false)
        pokemonRepository.fetchData(offset)

    }

    private class Handler(val pokemonViewModel: PokemonViewModel) :
        PokemonRepository.HandlerPokemonData() {

        private val logger = Logger.getLogger("HandlerPokemonData")

        override fun onSuccess(newPokemonList: List<Pokemon>) {
            pokemonViewModel.loading.postValue(false)
            pokemonViewModel.loadError.postValue(false)
            pokemonViewModel.pokemonList.addAll(newPokemonList)
            pokemonViewModel.pokemonLiveData.postValue(pokemonViewModel.pokemonList)
            logger.log(
                Level.INFO,
                "Success to call pokemonRepository.fetchData on PokemonList method"
            )
        }

        override fun onError(t: Throwable) {
            pokemonViewModel.loading.postValue(false)
            pokemonViewModel.loadError.postValue(true)
            logger.log(
                Level.SEVERE,
                "Error to call pokemonRepository.fetchData on PokemonList method"
            )
        }
    }
}