package com.example.javamovieapp.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.javamovieapp.data.api.MovieApi;
import com.example.javamovieapp.data.model.Movie;
import com.example.javamovieapp.data.model.MovieSearchResponse;
import com.example.javamovieapp.ui.main.MovieAdapter;
import com.example.javamovieapp.ui.AppStrings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchController {
    private final MovieApi movieApi;
    private final MovieAdapter adapter;
    private final Context context;

    public MovieSearchController(MovieApi api, MovieAdapter adapter, Context context) {
        this.movieApi = api;
        this.adapter = adapter;
        this.context = context;
    }

    public void searchMovies(String query) {
        Call<MovieSearchResponse> call = movieApi.searchMovies(query, AppStrings.API_KEY);

        call.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getSearch();
                    if (movies != null && !movies.isEmpty()) {
                        adapter.updateData(movies);
                    } else {
                        Toast.makeText(context, AppStrings.NO_RESULTS, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, AppStrings.ERROR_FETCHING, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                Toast.makeText(context, "Fout: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
