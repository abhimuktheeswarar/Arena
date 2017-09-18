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

package msa.arena.data.repository.datasources.remote;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.data.entities.remote.MovieSearchPojo;
import msa.data.entities.remote.list.MovieListPojo;
import msa.data.entities.remote.medi.SearchMedResult;
import msa.data.entities.remote.medi.SearchSubmit;
import msa.data.repository.datasources.remote.ArenaApi;
import retrofit2.http.Body;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */


@Deprecated
public final class MockArenaApi implements ArenaApi {

    private final BehaviorDelegate<ArenaApi> delegate;

    public MockArenaApi(BehaviorDelegate<ArenaApi> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Single<MovieSearchPojo> searchForMovie(@Query("query") String query) {
        return null;
    }

    @Override
    public Observable<MovieSearchPojo> searchForMovieObservable(@Query("query") String query) {
        return null;
    }

    @Override
    public Observable<MovieSearchPojo> searchMovie(@Query("query") String query) {
        return null;
    }

    @Override
    public Observable<MovieListPojo> getMovies1() {
        return null;
    }

    @Override
    public Flowable<MovieListPojo> getMovies() {
        return null;
    }

    @Override
    public Flowable<MovieListPojo> getMovies(@Query("page") int page) {
        return null;
    }

    @Override
    public Single<List<SearchMedResult>> getMedicineSuggestions(@Body SearchSubmit searchSubmit) {
        return null;
    }
}
