package msa.arena.data.repository;

import com.msa.domain.Repository;
import com.msa.domain.entities.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

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
    public Flowable<List<Movie>> getMovieFlow(int page) {
        return dataStoreFactory.createDummyDataSource().getMovieFlow(page);
    }

    @Override
    public Flowable<Movie> getMoviesTypeTwo(int page) {
        return dataStoreFactory.createRemoteDataSource().getMoviesTypeTwo(page);
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
