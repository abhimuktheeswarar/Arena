package msa.arena.data.repository.datasources.remote;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.arena.data.entities.remote.MovieSearchPojo;
import msa.arena.data.entities.remote.list.MovieListPojo;
import msa.arena.data.entities.remote.medi.SearchMedResult;
import msa.arena.data.entities.remote.medi.SearchSubmit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("discover/movie")
    Flowable<MovieListPojo> getMoviesTypeTwo(@Query("page") int page);

    @POST("api/serve_appointment/searchMedicine")
    Single<List<SearchMedResult>> getMedicineSuggestions(@Body SearchSubmit searchSubmit);
}
