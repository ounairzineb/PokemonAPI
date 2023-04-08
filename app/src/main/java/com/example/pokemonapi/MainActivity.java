package com.example.pokemonapi;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.pokemonapi.models.Pokemon;
import com.example.pokemonapi.models.PokemonResponse;
import com.example.pokemonapi.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "POKEDEX";
private Retrofit retrofit;
private RecyclerView recyclerView;
private PokemonListAdapter pokemonListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
pokemonListAdapter= new PokemonListAdapter();
recyclerView.setAdapter(pokemonListAdapter);
recyclerView.setHasFixedSize(true);

    GridLayoutManager layoutManager=new GridLayoutManager(this,3);
    recyclerView.setLayoutManager(layoutManager);


retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getData();
    }

    private void getData(){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getpokemonlist();

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
if (response.isSuccessful()){
    PokemonResponse pokemonResponse = response.body();
    System.out.println("hellooo: " +response.body().getResults().get(1));
     ArrayList<Pokemon> pokemonList = pokemonResponse.getResults();

     for(int i=0; i<pokemonList.size(); i++){
         pokemonListAdapter.addListPokemon(pokemonList);
     }

        pokemonListAdapter.addListPokemon(pokemonList);


}
else{
    Log.e(TAG, " onResponse: " + response.errorBody());
}
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }


}