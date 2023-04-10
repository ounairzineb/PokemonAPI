package com.example.pokemonapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokemonapi.models.Pokemon;
import com.example.pokemonapi.models.StatsList;
import com.example.pokemonapi.models.Type;
import com.example.pokemonapi.pokeapi.PokeapiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class details extends AppCompatActivity {
    private int id;
    private ImageView pokeImage;
    private TextView pokeName;
    private TextView height;
    private TextView weight;
    private Retrofit retrofit;
    private TextView type2;
    private TextView base;
    private String nameType;
    private Pokemon pokemonDetail;
    private final List<Type> typesData = new ArrayList<>();
    private final List<StatsList> stateData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        pokeImage = (ImageView) findViewById(R.id.pokeImage);
        pokeName = (TextView) findViewById(R.id.PokeName);
        type2 = (TextView) findViewById(R.id.type);
        base = (TextView) findViewById(R.id.base);
        height = (TextView) findViewById(R.id.height);
        weight = (TextView) findViewById(R.id.weight);

        Intent intent = getIntent(); // get Intent which we set from Previous Activity4
        id = intent.getIntExtra("id",0);
        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/"+id+".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(pokeImage);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeapiService repoServiceApi = retrofit.create(PokeapiService.class);
        Call<Pokemon> pokemonInfo = repoServiceApi.getPokemonDetail(id);

        pokemonInfo.enqueue(new Callback<Pokemon>() {

            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if(response.isSuccessful()){

                    Pokemon pokemon = response.body();
                    List<Type> typesList = pokemon.getTypes();

                    for (int i = 0; i < typesList.size(); i++) {
                        Type type = typesList.get(i);
                        nameType = type.getType().getName();
                        if(i==0){
                            type2.setText(nameType);
                        }
                    }

                    List<StatsList> stateList = pokemon.getStats();
                    for (int i = 0; i < stateList.size(); i++) {
                        StatsList state = stateList.get(i);
                        String baseState = state.getBaseStat();
                        String stateName = state.getState().getName();
                        //System.out.println("    :::"+baseState);
                        stateData.add(new StatsList(stateName,baseState));


                    }

                pokemonDetail = new Pokemon(pokemon.getName(), pokemon.getBaseExperience(),  pokemon.getHeight(), pokemon.getWeight(),pokemon.getStats(), pokemon.getTypes());
                    pokeName.setText("Name:"+pokemonDetail.getName());
                    height.setText("Height:"+pokemonDetail.getHeight());
                    //heightPoke.setText(heightFormatted);
                    //withPoke.setText(weightFormatted);
                    //progressViewExp.setProgress(pokemonInfo.getBaseExperience());
                    base.setText("B-E:"+pokemon.getBaseExperience()+"/1000");
                    weight.setText("Weight:"+pokemonDetail.getWeight());
                    type2.setText("Type:"+nameType);
                    System.out.println("resultat : "+pokemonDetail.toString());
                }else{
                    Log.e("Info", String.valueOf(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("Info", " Error: " + t.getMessage());
            }
        });


    }
}