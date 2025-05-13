package com.example.javamovieapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javamovieapp.data.api.MovieApi;

import com.example.javamovieapp.ui.main.MovieAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.javamovieapp.ui.AppStrings;
import com.example.javamovieapp.controller.MovieSearchController;



public class MainActivity extends AppCompatActivity {
    // UI elements
    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;

    // Adapter for displaying search results
    private MovieAdapter adapter;

    // Retrofit API interface
    private MovieApi movieApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);

        // Setup RecyclerView with layout and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

        // Initialize Retrofit for network communication
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppStrings.BASE_URL)// Base URL for the OMDb API
                .addConverterFactory(GsonConverterFactory.create())// Use Gson for JSON parsing
                .build();

        // Create API interface
        movieApi = retrofit.create(MovieApi.class);

        // Setup controller to handle search logic
        MovieSearchController controller = new MovieSearchController(movieApi, adapter, this);

        // Set listener for the search button
        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                controller.searchMovies(query);// Perform search
            } else {
                Toast.makeText(this, AppStrings.ERROR_NO_QUERY, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
