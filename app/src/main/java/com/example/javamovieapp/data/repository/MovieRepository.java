package com.example.javamovieapp.data.repository;

import com.example.javamovieapp.data.api.MovieApi;
import com.example.javamovieapp.data.model.Movie;
import com.example.javamovieapp.data.model.MovieSearchResponse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

/**
 * Deze repository wordt momenteel niet gebruikt,
 * maar is voorbereid voor toekomstige uitbreidingen
 * wanneer de applicatie groter wordt.
 */

public class MovieRepository {

    private static final String BASE_URL = "https://www.omdbapi.com/";
    private static final String API_KEY = "62e795df";
    private final MovieApi movieApi;

    public MovieRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);
    }

    public void searchMovies(String query, MovieSearchCallback callback) {
        Call<MovieSearchResponse> call = movieApi.searchMovies(query, API_KEY);
        call.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getSearch());
                } else {
                    callback.onError("Geen resultaten gevonden.");
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                callback.onError(t.getMessage() != null ? t.getMessage() : "Netwerkfout");
            }
        });
    }

    public interface MovieSearchCallback {
        void onSuccess(List<Movie> movies);
        void onError(String errorMessage);
    }
}
