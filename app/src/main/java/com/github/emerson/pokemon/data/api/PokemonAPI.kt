package com.github.emerson.pokemon.data.api

import com.github.emerson.pokemon.data.model.Pokemon
import io.reactivex.Single
import retrofit2.http.GET

interface PokemonAPI {

    @GET("pokemon")
    fun getPokemonsList() : Single<List<Pokemon>>
}