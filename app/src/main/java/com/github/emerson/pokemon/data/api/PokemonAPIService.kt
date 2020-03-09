package com.github.emerson.pokemon.data.api

import com.github.emerson.pokemon.data.model.Pokemon
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PokemonAPIService {
    private val BASE_URL = "https://pokeapi.co/api/v2/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PokemonAPI::class.java)

    fun getPokemonsList() = api.getPokemonsList()
    fun getPokemon(url: String) = api.getPokemon(url)
    fun getPokemonListWithUri(nextUri: String) = api.getPokemonListWithUri(nextUri)


}