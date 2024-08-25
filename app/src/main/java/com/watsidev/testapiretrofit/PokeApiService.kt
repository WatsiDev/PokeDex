package com.watsidev.testapiretrofit

import com.watsidev.testapiretrofit.Model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon?offset=0&limit=151")
    suspend fun getPokemons(): PokemonResponse

    @GET("pokemon/{id}/")
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonResponse
}