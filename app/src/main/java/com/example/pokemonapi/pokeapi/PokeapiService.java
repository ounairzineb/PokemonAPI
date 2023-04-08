package com.example.pokemonapi.pokeapi;

import com.example.pokemonapi.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonResponse> getpokemonlist();
}
