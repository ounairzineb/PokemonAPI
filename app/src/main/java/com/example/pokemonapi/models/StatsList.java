package com.example.pokemonapi.models;

import com.google.gson.annotations.SerializedName;

public class StatsList {
    private String nameState;

    @SerializedName("base_stat")
    private String baseStat;

    public StatsList(String nameState, String baseStat) {
        this.nameState = nameState;
        this.baseStat = baseStat;
    }

    public String getNameState() {
        return nameState;
    }

    @SerializedName("stat")
    public StatsList.State state;

    public State getState() {
        return state;
    }

    public String getBaseStat() {
        return baseStat;
    }

    public static class State {

        @SerializedName("name")
        public String name;

        public String getName() {
            return name;
        }
    }
}
