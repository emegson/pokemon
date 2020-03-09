package com.github.emerson.pokemon.data.repository

import com.github.emerson.pokemon.data.api.PokemonAPIService
import com.github.emerson.pokemon.data.model.Pokemon
import com.github.emerson.pokemon.data.model.PokemonListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.PriorityBlockingQueue
import java.util.logging.Level
import java.util.logging.Logger

class PokemonRepository(private val handler: HandlerPokemonData) {

    private var newPokemons = PokemonListResponse()
    private val api = PokemonAPIService()
    private val disposable = CompositeDisposable()
    private var shouldShowData = -1
    private var requestsCounter = 964

    private val priorityQueue = PriorityBlockingQueue<Pokemon>()

    private val logger = Logger.getLogger("PokemonRepository")

    fun fetchData(offset: Int) {
        val s = "https://pokeapi.co/api/v2/pokemon?offset=${offset}&limit=20"
        getPokemonListWithUri(s)
    }

    private fun getPokemonListWithUri(s: String) {
        logger.log(Level.INFO, "Start getPokemonList on PokemonRepository")
        disposable.add(
            api.getPokemonListWithUri(s)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableSingleObserver<PokemonListResponse>() {
                    override fun onSuccess(pokemonListResponse: PokemonListResponse) {
                        logger.log(Level.INFO, "onSuccess getInitialPokemonList on ${Thread.currentThread()}")
                        newPokemons = pokemonListResponse
                        shouldShowData = pokemonListResponse.results?.size ?: 1.unaryMinus()
                        logger.log(
                            Level.INFO,
                            "Now we have ${requestsCounter} pokemons to catch"
                        )
                        logger.log(Level.INFO, "We found more ${shouldShowData} new pokemons")
                        fetchPokemonDataFromRemote()
                        logger.log(Level.FINE, newPokemons.toString())
                    }

                    override fun onError(e: Throwable) {
                        handler.onError(e)
                    }
                })
        )
    }

    fun getPokemonListFromLocal() {
        //TODO
    }

    private fun fetchPokemonDataFromRemote() {
        newPokemons.results?.forEach {
            it.url?.let { url ->
                disposable.add(
                    api.getPokemon(url)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Pokemon>() {
                            override fun onSuccess(newPokemon: Pokemon) {
                                logger.log(Level.INFO, "onSuccess ${newPokemon.toString()}")
                                updateCounter()
                                while (!priorityQueue.add(newPokemon)) {
                                    logger.log(
                                        Level.SEVERE,
                                        "Trying to put pokemons on PriorityQueue ${newPokemon.toString()} on ${Thread.currentThread()}"
                                    )
                                }
                                logger.log(
                                    Level.WARNING,
                                    "Still missing ${requestsCounter} pokemons to catch"
                                )
                                logger.log(
                                    Level.WARNING,
                                    "Still missing ${shouldShowData} new pokemons to show data"
                                )
                                logger.log(
                                    Level.FINE,
                                    "onSuccess newPokemon ${newPokemon.toString()} fetched"
                                )
                                shouldSendData()
                            }

                            override fun onError(e: Throwable) {
                                handler.onError(e)
                            }

                        })
                )
            }

        }

    }

    @Synchronized
    private fun shouldSendData() {
        when (shouldShowData) {
            0 -> {
                val pokemonList = arrayListOf<Pokemon>()
                logger.info("Starting to put pokemons on list")
                priorityQueue.drainTo(pokemonList)
                priorityQueue.clear()
                handler.onSuccess(pokemonList)
                logger.info("Finished putting pokemons on list")
                restartCounter()
            }
            else ->
                logger.log(
                    Level.WARNING,
                    "Still missing ${20 - priorityQueue.size} new pokemons to show data"
                )
        }
    }

    @Synchronized
    fun updateCounter() {
        shouldShowData--
        requestsCounter--
    }

    @Synchronized
    fun restartCounter() {
        shouldShowData = -1
    }

    abstract class HandlerPokemonData() {
        abstract fun onError(t: Throwable)
        abstract fun onSuccess(newPokemonList: List<Pokemon>)
    }
}