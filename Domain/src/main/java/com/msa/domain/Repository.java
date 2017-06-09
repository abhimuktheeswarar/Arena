package com.msa.domain;

import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.entities.User;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public interface Repository {


    Observable<User> getUser();

    Completable updateUser(User user);

    Observable<Movie> getMovies(int page);

    Observable<List<Movie>> getMovieList(int page);

    Observable<List<Movie>> getMovieList2(int page);

    Observable<LinkedHashMap<String, Movie>> getMovieHashes(int page);

    Flowable<List<Movie>> getMovieFlow(int page);

    Flowable<Movie> getMoviesTypeTwo(int page);

    Flowable<Lce<Movie>> getMoviesTypeTwoLce(int page);

    Observable<List<Movie>> searchMovie(String query);

    Single<List<Movie>> searchForMovie(String query);

}
