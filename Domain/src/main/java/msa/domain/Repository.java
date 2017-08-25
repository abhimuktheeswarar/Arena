package msa.domain;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;
import msa.domain.entities.User;
import msa.domain.holder.carrier.ResourceCarrier;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public interface Repository {


    Observable<User> getUser();

    Completable updateUser(User user);

    Observable<Movie> getMovies1(int page);

    Observable<List<Movie>> getMovieList(int page);

    Observable<List<Movie>> getMovieList2(int page);

    Observable<LinkedHashMap<String, Movie>> getMovieHashes(int page);

    Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLce(int page);

    Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLceR(int page);

    Flowable<List<Movie>> getMovieFlow(int page);

    Flowable<Movie> getMoviesTypeTwo(int page);

    Flowable<Lce<Movie>> getMoviesTypeTwoLce(int page);

    Observable<List<Movie>> searchMovie(String query);

    Single<List<Movie>> searchForMovie(String query);

    Completable setFavoriteMovie(String movieId, boolean isFavorite);

    Single<ResourceCarrier<LinkedHashMap<String, Movie>>> searchMoviesSingle(String query);

    Observable<ResourceCarrier<LinkedHashMap<String, Movie>>> searchMoviesObservable(String query);

    Flowable<ResourceCarrier<LinkedHashMap<String, Movie>>> getMovies(int page);



}
