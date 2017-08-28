package msa.data.repository.datasources.local.realm.realmobjects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Abhimuktheeswarar on 12-06-2017.
 */

public class MovieR extends RealmObject {

    @PrimaryKey
    private String movieId;

    private String movieName;

    private boolean isFavorite;

    private int releaseYear;

    public MovieR() {
    }

    public MovieR(String movieId, boolean isFavorite) {
        this.movieId = movieId;
        this.isFavorite = isFavorite;
    }

    public MovieR(String movieId, String movieName, int releaseYear) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseYear = releaseYear;
    }

    public MovieR(String movieId, String movieName, int releaseYear, boolean isFavorite) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.isFavorite = isFavorite;
    }

    public String getMovieId() {
        return movieId;
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
}
