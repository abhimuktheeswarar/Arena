package msa.arena.data.repository;

import com.msa.domain.entities.Movie;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public interface BaseDataSource {

    Observable<Movie> getMovies(int page);

    Flowable<Movie> getMoviesTypeTwo(int page);
}
