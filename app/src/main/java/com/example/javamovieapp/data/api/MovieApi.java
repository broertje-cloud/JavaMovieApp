package com.example.javamovieapp.data.api;

import com.example.javamovieapp.data.model.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("/")
    Call<MovieSearchResponse> searchMovies(
            @Query("s") String query,
            @Query("apikey") String apiKey
    );
}
