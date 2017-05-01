package msa.arena.data.repository.datasources.dummy;

import com.msa.domain.entities.Movie;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import msa.arena.data.repository.BaseDataSource;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class DummyDataSource implements BaseDataSource {

    @Override
    public Observable<Movie> getMovies(int page) {
        List<Movie> movieList = new LinkedList<>();
        for (int i = page; i < page + 10; i++) movieList.add(new Movie("id" + i, "Movie " + i));
        return Observable.fromIterable(movieList);
    }
}
