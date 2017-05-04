package msa.arena.data.repository.datasources.remote;

import org.intellij.lang.annotations.Flow;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.arena.data.entities.remote.MovieSearchPojo;
import msa.arena.data.entities.remote.list.MovieListPojo;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public interface ArenaApi {


    @GET("search/movie")
    Single<MovieSearchPojo> searchForMovie(@Query("query") String query);

    @GET("search/movie")
    Observable<MovieSearchPojo> searchMovie(@Query("query") String query);

    @GET("discover/movie?&language=en-US&sort_by=popularity.desc&page=1")
    Observable<MovieListPojo> getMovies();

    @GET("discover/movie?&language=en-US&sort_by=popularity.desc&page=1")
    Flowable<MovieListPojo> getMoviesTypeTwo();
}
