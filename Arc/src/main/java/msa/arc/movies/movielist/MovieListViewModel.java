package msa.arc.movies.movielist;

import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.usecases.GetMoviesLce;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import msa.arc.base.BaseViewModel;

/**
 * Created by Abhimuktheeswarar on 11-06-2017.
 */

public class MovieListViewModel extends BaseViewModel {

    private final GetMoviesLce getMoviesLce;
    Lce<LinkedHashMap<String, Movie>> linkedHashMapLce;
    private PublishProcessor<Integer> paginator;

    @Inject
    public MovieListViewModel(GetMoviesLce getMoviesLce) {
        this.getMoviesLce = getMoviesLce;
    }

    Flowable<Lce<LinkedHashMap<String, Movie>>> getMovieList(int page) {
        return getMoviesLce.execute(GetMoviesLce.Params.setPage(page)).doOnNext(linkedHashMapLce1 -> MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce1);
        /*return paginator.concatMap(new Function<Integer, Flowable<Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Flowable<Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Integer page) throws Exception {
                return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));
            }
        }).doOnNext(linkedHashMapLce1 -> MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce1);*/
    }
}
