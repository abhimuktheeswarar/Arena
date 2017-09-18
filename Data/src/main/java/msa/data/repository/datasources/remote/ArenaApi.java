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

package msa.data.repository.datasources.remote;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.data.entities.remote.MovieSearchPojo;
import msa.data.entities.remote.list.MovieListPojo;
import msa.data.entities.remote.medi.SearchMedResult;
import msa.data.entities.remote.medi.SearchSubmit;
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
    Observable<MovieSearchPojo> searchForMovieObservable(@Query("query") String query);

    @GET("search/movie")
    Observable<MovieSearchPojo> searchMovie(@Query("query") String query);

    @GET("discover/movie?&language=en-US&sort_by=popularity.desc&page=1")
    Observable<MovieListPojo> getMovies1();

    @GET("discover/movie?&language=en-US&sort_by=popularity.desc&page=1")
    Flowable<MovieListPojo> getMovies();

    @GET("discover/movie")
    Flowable<MovieListPojo> getMovies(@Query("page") int page);



    @POST("api/serve_appointment/searchMedicine")
    Single<List<SearchMedResult>> getMedicineSuggestions(@Body SearchSubmit searchSubmit);
}
