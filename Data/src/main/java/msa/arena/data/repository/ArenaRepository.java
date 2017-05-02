package msa.arena.data.repository;

import com.msa.domain.Repository;
import com.msa.domain.entities.Movie;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;

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
        return dataStoreFactory.createDummyDataSource().getMovies(page);
    }

    @Override
    public Flowable<Movie> getMoviesTypeTwo(int page) {
        return dataStoreFactory.createDummyDataSource().getMoviesTypeTwo(page);
    }
}
