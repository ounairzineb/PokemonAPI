package com.example.pokemonapi;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    private int offset;
    private boolean fitToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonListAdapter= new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if(dy>0){
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems= layoutManager.findFirstVisibleItemPosition();
                if (fitToLoad){
                    if((visibleItemCount + pastVisibleItems)>=totalItemCount){
                        Log.i(TAG, "Zaicha");
                        fitToLoad = false;
                        offset+= 20;
                        getData(offset);
                    }
                }
            }
        }
    });

            retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            fitToLoad = true;
            offset=0;
            getData(offset);

        recyclerView.addOnItemTouchListener(
                new Listener(this, recyclerView ,new Listener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent = new Intent(MainActivity.this, details.class);
                        intent.putExtra("id", position+1);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        }

    private void getData(int offset){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getpokemonlist(20,offset);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                fitToLoad = true;

                if (response.isSuccessful()){
                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonResponse.getResults();
                    pokemonListAdapter.addListPokemon(pokemonList);
                }
                else{
                    Log.e("test", " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                fitToLoad = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }


}