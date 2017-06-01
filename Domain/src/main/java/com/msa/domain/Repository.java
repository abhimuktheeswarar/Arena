package com.msa.domain;

import com.msa.domain.entities.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public interface Repository {


    Observable<Movie> getMovies(int page);

    Observable<List<Movie>> getMovieList(int page);

    Flowable<List<Movie>> getMovieFlow(int page);

    Flowable<Movie> getMoviesTypeTwo(int page);

    Observable<List<Movie>> searchMovie(String query);

    Single<List<Movie>> searchForMovie(String query);

}
