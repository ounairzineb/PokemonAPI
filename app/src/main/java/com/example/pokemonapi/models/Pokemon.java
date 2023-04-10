package com.example.pokemonapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokemon {
private int number;
    private String name;
    private String url;
    @SerializedName("base_experience")
    private String baseExperience;

    private String height;
    private String weight;

    @SerializedName("stats")
    private List<StatsList> stats;
    @SerializedName("types")
    private List<Type> types;


    public int getNumber() {
        String[] urlp=url.split("/");
        return Integer.parseInt(urlp[urlp.length - 1]);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getBaseExperience() {
        return baseExperience;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public List<StatsList> getStats() {
        return stats;
    }

    public List<Type> getTypes() {
        return types;
    }

    public Pokemon(String name, String baseExperience, String height, String weight, List<StatsList> stats, List<Type> types) {
        this.name = name;
        this.baseExperience = baseExperience;
        this.height = height;
        this.weight = weight;
        this.stats = stats;
        this.types = types;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", baseExperience='" + baseExperience + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", stats=" + stats +
                ", types=" + types +
                '}';
    }
}
