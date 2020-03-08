package com.github.emerson.pokemon.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.emerson.pokemon.data.api.PokemonAPIService
import com.github.emerson.pokemon.data.model.Pokemon
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.logging.Level
import java.util.logging.Logger

class PokemonViewModel : ViewModel() {

    private val api = PokemonAPIService()
    private val disposable = CompositeDisposable()

    private val pokemonList: ArrayList<Pokemon> = ArrayList()
    private val logger = Logger.getLogger("PokemonViewModel")
    val pokemonLiveData = MutableLiveData<List<Pokemon>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun refresh() {
        logger.log(Level.INFO, "REFRESH")
        loading.postValue(true)
        pokemonLiveData.postValue(pokemonList)
        getPokemonList()

    }

    private fun getPokemonList() {
        loading.postValue(true)
        disposable.add(
            api.getPokemonsList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Pokemon>>() {
                    override fun onSuccess(newPokemons: List<Pokemon>) {
                        pokemonList.addAll(newPokemons)
                        pokemonLiveData.postValue(pokemonList)
                        loadError.postValue(false)
                        loading.postValue(false)
                        logger.log(Level.FINE, "getPokemonList Success")
                    }

                    override fun onError(e: Throwable) {
                        loadError.postValue(true)
                        loading.postValue(false)
                        logger.log(Level.SEVERE, "getPokemonList", e)
                    }

                })
        )
    }

}
