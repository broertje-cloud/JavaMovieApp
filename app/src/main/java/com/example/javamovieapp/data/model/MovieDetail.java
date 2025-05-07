package com.example.javamovieapp.data.model;

import com.google.gson.annotations.SerializedName;

public class MovieDetail {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Poster")
    private String poster;

    @SerializedName("Plot")
    private String plot;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPoster() {
        return poster;
    }

    public String getPlot() {
        return plot;
    }
}
