package com.example.pokemonapi.models;

import com.google.gson.annotations.SerializedName;

public class Type {
    public String nameType;

    public Type(String nameType) {
        this.nameType = nameType;
    }

    public String getNameType() {
        return nameType;
    }

    @SerializedName("type")
    public TypePok type;

    public TypePok getType() {
        return type;
    }

    public static class TypePok {

        @SerializedName("name")
        public String name;

        public String getName() {
            return name;
        }
    }
}
