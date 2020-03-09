package com.github.emerson.pokemon.data.api

import com.github.emerson.pokemon.data.model.Pokemon
import com.github.emerson.pokemon.data.model.PokemonListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonAPI {

    @GET("pokemon")
    fun getPokemonsList(): Single<PokemonListResponse>

    @GET
    fun getPokemon(@Url url: String): Single<Pokemon>

    @GET
    fun getPokemonListWithUri(@Url url: String): Single<PokemonListResponse>
}