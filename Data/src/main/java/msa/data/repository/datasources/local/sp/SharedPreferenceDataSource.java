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

package msa.data.repository.datasources.local.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.data.repository.BaseDataSource;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;
import msa.domain.entities.User;
import msa.domain.holder.carrier.ResourceCarrier;

/**
 * Created by Abhimuktheeswarar on 09-06-2017.
 */

public class SharedPreferenceDataSource implements BaseDataSource {


    private final String TAG = SharedPreferenceDataSource.class.getSimpleName();
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final Observable<User> userObservable;

    private final String USER_ID = "userId";
    private final String DISPLAY_NAME = "displayName";

    public SharedPreferenceDataSource(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        userObservable = Observable.create(e -> {
            SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = (sharedPreferences1, key) -> {
                if (key.equals(USER_ID) || key.equals(DISPLAY_NAME)) {
                    e.onNext(getUserData());
                }
            };
            if (!e.isDisposed()) {
                e.onNext(getUserData());
                sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            } else {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            }

        });
    }

    private User getUserData() {
        return new User(sharedPreferences.getString(USER_ID, "id_"), sharedPreferences.getString(DISPLAY_NAME, "NA"));
    }


    @Override
    public Observable<User> getUser() {
        return userObservable;
    }

    @Override
    public Completable updateUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, user.getUserId()).apply();
        editor.putString(DISPLAY_NAME, user.getDisplayName());
        if (editor.commit()) return Completable.complete();
        else return Completable.error(new Throwable("Error updating user"));
    }

    @Override
    public Observable<Movie> getMovies1(int page) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getMovieList(int page) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getMovieList2(int page) {
        return null;
    }

    @Override
    public Observable<LinkedHashMap<String, Movie>> getMovieHashes(int page) {
        return null;
    }

    @Override
    public Flowable<List<Movie>> getMovieFlow(int page) {
        return null;
    }

    @Override
    public Flowable<Movie> getMoviesTypeTwo(int page) {
        return null;
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
        return null;
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
}
