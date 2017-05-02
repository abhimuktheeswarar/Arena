package msa.arena.data.repository.datasources.remote;

import com.msa.domain.entities.Movie;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import msa.arena.data.repository.BaseDataSource;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class RemoteDataSource implements BaseDataSource {

    private final ArenaApi arenaApi;

    public RemoteDataSource(ArenaApi arenaApi) {
        this.arenaApi = arenaApi;
    }

    @Override
    public Observable<Movie> getMovies(int page) {
        return null;
    }

    @Override
    public Flowable<Movie> getMoviesTypeTwo(int page) {
        return null;
    }
}
