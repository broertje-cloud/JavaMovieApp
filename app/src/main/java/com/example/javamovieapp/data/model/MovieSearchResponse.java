package com.example.javamovieapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResponse {

    @SerializedName("Search")
    private List<Movie> search;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("Response")
    private String response;

    public List<Movie> getSearch() {
        return search;
    }

}
