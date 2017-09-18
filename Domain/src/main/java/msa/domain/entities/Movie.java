/*
 * Copyright 2017, Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package msa.domain.entities;

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
