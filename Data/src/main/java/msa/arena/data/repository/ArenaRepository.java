package msa.arena.data.repository;

import com.msa.domain.Repository;
import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.entities.User;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.arena.data.repository.datasources.remote.RemoteDataSource;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

@Singleton
public class ArenaRepository implements Repository {

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
}
