package com.example.pokemonapi.pokeapi;

import com.example.pokemonapi.models.Pokemon;
import com.example.pokemonapi.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonResponse> getpokemonlist(@Query("limit") int limit, @Query("offset")int offset);
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemonDetail(@Path("id") int id);
}