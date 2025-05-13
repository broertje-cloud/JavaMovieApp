package com.example.javamovieapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.javamovieapp.data.api.MovieApi;
import com.example.javamovieapp.data.model.MovieDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.javamovieapp.ui.AppStrings;

public class DetailActivity extends AppCompatActivity {

    // Key used to retrieve IMDb ID from the intent
    public static final String EXTRA_IMDB_ID = "imdbID";

    // UI components
    private TextView titleTextView, yearTextView, plotTextView;
    private ImageView posterImageView;

    // API key used for the OMDb request
    private static final String API_KEY = "62e795df";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize UI elements
        titleTextView = findViewById(R.id.titleTextView);
        yearTextView = findViewById(R.id.yearTextView);
        plotTextView = findViewById(R.id.plotTextView);
        posterImageView = findViewById(R.id.posterImageView);

        // Get the IMDb ID passed from the previous screen
        String imdbID = getIntent().getStringExtra(EXTRA_IMDB_ID);

        // Fetch the movie details if ID is available
        if (imdbID != null) {
            fetchMovieDetail(imdbID);
        } else {
            Toast.makeText(this, AppStrings.ERROR_NO_IMDB_ID, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchMovieDetail(String imdbID) {
        // Set up Retrofit with base URL and JSON converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppStrings.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Create the API instance
        MovieApi api = retrofit.create(MovieApi.class);

        // Make the API call
        Call<MovieDetail> call = api.getMovieDetail(imdbID, API_KEY);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // If successful, populate the UI with movie data
                    MovieDetail detail = response.body();
                    titleTextView.setText(detail.getTitle());
                    yearTextView.setText(detail.getYear());
                    plotTextView.setText(detail.getPlot());

                    Glide.with(DetailActivity.this)
                            .load(detail.getPoster())
                            .into(posterImageView);
                } else {
                    // Show error message if response is invalid
                    Toast.makeText(DetailActivity.this, AppStrings.ERROR_NO_DETAILS_FOUND, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                // Show error message if request fails
                Toast.makeText(DetailActivity.this, AppStrings.ERROR_DETAIL_LOAD_FAILED + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
