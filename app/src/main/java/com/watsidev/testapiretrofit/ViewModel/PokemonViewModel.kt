package com.watsidev.testapiretrofit.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.watsidev.testapiretrofit.Model.PokemonResponse
import com.watsidev.testapiretrofit.RetrofitInstance


class PokemonViewModel : ViewModel() {

    private val _pokemonsDetail = mutableStateOf<List<PokemonResponse>>(emptyList())
    val pokemonsDetail: State<List<PokemonResponse>> = _pokemonsDetail

    init {
        fetchPokemonsDetail()
    }

    private fun fetchPokemonsDetail() {
        viewModelScope.launch {
            val pokemonDetails = mutableListOf<PokemonResponse>()
            try {
                for (i in 1..151) { // Itera sobre los primeros 151 Pokémon
                    val response = RetrofitInstance.api.getPokemonDetail(i)
                    pokemonDetails.add(response)
                }
                _pokemonsDetail.value = pokemonDetails
            } catch (e: Exception) {
                // Manejar errores
            }
        }
    }
}