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

package msa.data.repository.datasources.dummy;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import org.reactivestreams.Publisher;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import msa.data.repository.BaseDataSource;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;
import msa.domain.entities.User;
import msa.domain.holder.carrier.ResourceCarrier;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class DummyDataSource implements BaseDataSource {

    private static final String TAG = DummyDataSource.class.getSimpleName();
    private final Context context;
    Observable<Movie> movieObservable;
    Flowable<Movie> movieFlowable;

    public DummyDataSource(Context context) {
        this.context = context;
    }

    private <V> Observable<V> getObs(Observable<V> observable) {
        return ReactiveNetwork.observeNetworkConnectivity(context).switchMap(new Function<Connectivity, ObservableSource<? extends V>>() {
            @Override
            public ObservableSource<? extends V> apply(@NonNull Connectivity connectivity) throws Exception {
                if (connectivity.isAvailable()) return observable;
                else return Observable.error(new Throwable("no internet"));
            }
        });
    }

    @Override
    public Observable<User> getUser() {
        return Observable.just(new User("id1234", "Iron Man"));
    }

    @Override
    public Completable updateUser(User user) {
        return null;
    }

    @Override
    public Observable<Movie> getMovies1(int page) {
        List<Movie> movieList = new LinkedList<>();
        for (int i = page; i < page + 10; i++) {
            movieList.add(new Movie(UUID.randomUUID().toString() + i, "Movie " + i, i % 2 == 0));
        }
        //if (page > 5) movieList.add(new Movie());
        return Observable.fromIterable(movieList);
    }

    @Override
    public Observable<List<Movie>> getMovieList(int page) {
        List<Movie> movieList = new LinkedList<>();
        for (int i = page; i < page + 30; i++)
            movieList.add(new Movie(UUID.randomUUID().toString() + i, "Movie " + i, i % 2 == 0));
        return Observable.just(movieList);
    }

    @Override
    public Observable<List<Movie>> getMovieList2(int page) {
        return null;
    }

    @Override
    public Observable<LinkedHashMap<String, Movie>> getMovieHashes(int page) {
        LinkedHashMap<String, Movie> movieHashMap = new LinkedHashMap<>();
        for (int i = page; i < page + 30; i++) {
            Movie movie = new Movie(UUID.randomUUID().toString() + i, "Movie " + i, i % 2 == 0);
            movieHashMap.put(movie.getMovieId(), movie);
        }

        return Observable.just(movieHashMap);
    }

    @Override
    public Flowable<List<Movie>> getMovieFlow(int page) {
        List<Movie> movieList = new LinkedList<>();
        for (int i = page; i < page + 20; i++)
            movieList.add(new Movie("id" + i, "Movie " + i, i % 2 == 0));
        return Flowable.just(true).map(new Function<Boolean, List<Movie>>() {
            @Override
            public List<Movie> apply(@NonNull Boolean aBoolean) throws Exception {
                return movieList;
            }
        });
    }

    @Override
    public Flowable<Movie> getMoviesTypeTwo(int page) {
        List<Movie> movieList = new LinkedList<>();

        for (int i = page; i < page + 10; i++)
            movieList.add(new Movie("id" + i, "Movie " + i, i % 2 == 0));
        if (page > 20) movieList.add(new Movie());
        return Flowable.fromIterable(movieList);
        //if (page < 5) return Flowable.fromIterable(movieList);
        //else return Flowable.empty();

    }

    @Override
    public Flowable<Lce<Movie>> getMoviesTypeTwoLce(int page) {
        return null;
    }

    @Override
    public Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLce(int page) {
        return null;
    }

    @Override
    public Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLceR(int page) {
        return null;
    }

    @Override
    public Observable<List<Movie>> searchMovie(String query) {
        Log.d(TAG, "Searching -> " + query);
        List<Movie> movieList = new LinkedList<>();
        for (int i = 0; i < 5; i++)
            movieList.add(new Movie("id" + i, "Movie " + query + i, i % 2 == 0));
        if (query.equals("error")) return Observable.error(new NetworkErrorException());
        else return Observable.just(movieList);
    }

    @Override
    public Single<List<Movie>> searchForMovie(String query) {
        return null;
    }

    @Override
    public Completable setFavoriteMovie(String movieId, boolean isFavorite) {
        return null;
    }

    @Override
    public Single<ResourceCarrier<LinkedHashMap<String, Movie>>> searchMovies(String query) {
        return null;
    }

    @Override
    public Observable<ResourceCarrier<LinkedHashMap<String, Movie>>> searchMoviesObservable(String query) {
        return null;
    }

    @Override
    public Flowable<ResourceCarrier<LinkedHashMap<String, Movie>>> getMovies(int page) {
        return null;
    }

    public Flowable<Movie> getMoviesTypeThree(int page) {
        return movieObservable.map(new Function<Movie, Movie>() {
            @Override
            public Movie apply(@NonNull Movie movie) throws Exception {
                return null;
            }
        }).toFlowable(BackpressureStrategy.BUFFER);

    }

    public Flowable<Movie> getMoviesTypeFour(PublishProcessor<Integer> publishProcessor) {
        List<Movie> movieList = new LinkedList<>();
        return publishProcessor.concatMap(new Function<Integer, Publisher<Movie>>() {
            @Override
            public Publisher<Movie> apply(@NonNull Integer page) throws Exception {
                for (int i = page; i < page + 10; i++)
                    movieList.add(new Movie("id" + i, "Movie " + i, i % 2 == 0));
                if (page < 5) return Flowable.fromIterable(movieList);
                else {
                    publishProcessor.onComplete();
                    return Flowable.empty();
                }
            }
        });
    }
}
