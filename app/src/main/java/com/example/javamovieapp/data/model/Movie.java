package com.example.javamovieapp.data.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("imdbID")
    private String imdbID;

    @SerializedName("Type")
    private String type;

    @SerializedName("Poster")
    private String poster;

    // Getters
    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getImdbID() { return imdbID; }
    public String getType() { return type; }
    public String getPoster() { return poster; }
}

