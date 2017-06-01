package com.msa.domain.entities;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class Movie {

    private String movieId, movieName;

    private boolean isFavorite;

    public Movie() {
    }

    public Movie(String movieId, String movieName, boolean isFavorite) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.isFavorite = isFavorite;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public String toString() {
        return movieName;
    }
}
