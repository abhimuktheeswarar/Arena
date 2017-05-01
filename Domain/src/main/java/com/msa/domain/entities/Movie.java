package com.msa.domain.entities;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class Movie {

    private final String movieId, movieName;

    public Movie(String movieId, String movieName) {
        this.movieId = movieId;
        this.movieName = movieName;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }
}
