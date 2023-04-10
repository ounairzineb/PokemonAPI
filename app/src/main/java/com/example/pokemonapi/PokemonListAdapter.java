package com.example.pokemonapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokemonapi.models.Pokemon;

import java.util.ArrayList;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder>{
    private ArrayList<Pokemon> dataset;
    private Context context;

    public PokemonListAdapter(Context context) {
        this.context=context;
        dataset= new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pok,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.TextViewNumber.setText(p.getName());

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/"+ p.getNumber()+".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ImageViewPhoto);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addListPokemon(ArrayList<Pokemon> pokemonList) {

        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ImageViewPhoto;
        private TextView TextViewNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageViewPhoto= (ImageView) itemView.findViewById(R.id.ImageViewPhoto);
            TextViewNumber = (TextView) itemView.findViewById(R.id.TextViewNumber);

        }
    }
}
