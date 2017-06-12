package com.msa.domain.entities;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class Movie {

    private String movieId, movieName;

    private int releaseYear;

    private boolean isFavorite;

    public Movie() {
    }

    public Movie(String movieId, String movieName, boolean isFavorite) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.isFavorite = isFavorite;
    }

    public Movie(String movieId, String movieName, int releaseYear, boolean isFavorite) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.isFavorite = isFavorite;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return movieName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!movieId.equals(movie.movieId)) return false;
        return movieName.equals(movie.movieName);

    }

    @Override
    public int hashCode() {
        int result = movieId.hashCode();
        result = 31 * result + movieName.hashCode();
        return result;
    }
}
