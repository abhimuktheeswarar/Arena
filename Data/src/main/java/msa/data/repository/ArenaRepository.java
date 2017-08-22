package msa.data.repository;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.data.repository.datasources.local.realm.RealmDataSource;
import msa.data.repository.datasources.remote.RemoteDataSource;
import msa.domain.Repository;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;
import msa.domain.entities.User;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

@Singleton
public class ArenaRepository implements Repository {


    private final static String TAG = ArenaRepository.class.getSimpleName();

    private final DataStoreFactory dataStoreFactory;

    @Inject
    ArenaRepository(DataStoreFactory dataStoreFactory) {
        this.dataStoreFactory = dataStoreFactory;
    }


    @Override
    public Observable<User> getUser() {
        return dataStoreFactory.createSharedPreferenceDataSource().getUser();
    }

    @Override
    public Completable updateUser(User user) {
        return dataStoreFactory.createSharedPreferenceDataSource().updateUser(user);
    }

    @Override
    public Observable<Movie> getMovies(int page) {
        //return dataStoreFactory.createRemoteDataSource().getMovies(page);
        //return dataStoreFactory.createRemoteDataSource().getMovies(page);
        return dataStoreFactory.createDummyDataSource().getMovies(page);

    }

    @Override
    public Observable<List<Movie>> getMovieList(int page) {
        return dataStoreFactory.createDummyDataSource().getMovieList(page);
    }

    @Override
    public Observable<List<Movie>> getMovieList2(int page) {
        return null;
    }

    @Override
    public Observable<LinkedHashMap<String, Movie>> getMovieHashes(int page) {
        return dataStoreFactory.createDummyDataSource().getMovieHashes(page);
    }

    @Override
    public Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLce(int page) {
        RemoteDataSource remoteDataSource = dataStoreFactory.createRemoteDataSource();
        return remoteDataSource.getMoviesLce(page);
    }

    @Override
    public Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLceR(int page) {
        Log.d(TAG, "getMoviesLceR page = " + page);
        RealmDataSource realmDataSource = dataStoreFactory.createRealmDataStore();
        return realmDataSource.getMoviesLceR(page);
        //return dataStoreFactory.createRealmDataStore().getMoviesLceR(page);
    }

    @Override
    public Flowable<List<Movie>> getMovieFlow(int page) {
        return dataStoreFactory.createDummyDataSource().getMovieFlow(page);
    }

    @Override
    public Flowable<Movie> getMoviesTypeTwo(int page) {
        return dataStoreFactory.createRemoteDataSource().getMoviesTypeTwo(page);
    }

    @Override
    public Flowable<Lce<Movie>> getMoviesTypeTwoLce(int page) {
        return dataStoreFactory.createRemoteDataSource().getMoviesTypeTwoLce(page);
    }

    @Override
    public Observable<List<Movie>> searchMovie(String query) {
        return dataStoreFactory.createRemoteDataSource().searchMovie(query);
    }

    @Override
    public Single<List<Movie>> searchForMovie(String query) {
        return dataStoreFactory.createRemoteDataSource().searchForMovie(query);
    }

    @Override
    public Completable setFavoriteMovie(String movieId, boolean isFavorite) {
        return dataStoreFactory.createRealmDataStore().setFavoriteMovie(movieId, isFavorite);
    }
}
