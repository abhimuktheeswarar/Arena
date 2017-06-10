package msa.arc.movies.movielist;

import android.util.Log;

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

    private static final String TAG = MovieListViewModel.class.getSimpleName();
    private final GetMoviesLce getMoviesLce;
    private Lce<LinkedHashMap<String, Movie>> linkedHashMapLce;
    private PublishProcessor<Lce<LinkedHashMap<String, Movie>>> lcePublishProcessor;

    @Inject
    public MovieListViewModel(GetMoviesLce getMoviesLce) {
        this.getMoviesLce = getMoviesLce;
        lcePublishProcessor = PublishProcessor.create();
    }

    Flowable<Lce<LinkedHashMap<String, Movie>>> getMovieList(int page) {
        return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));

       /* return getMoviesLce.execute(GetMoviesLce.Params.setPage(page)).scan(new BiFunction<Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce1, @NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce2) throws Exception {
                MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce1;
                MovieListViewModel.this.linkedHashMapLce.getData().putAll(linkedHashMapLce2.getData());
                return MovieListViewModel.this.linkedHashMapLce;
            }
        });*/

        //WORKING
        /*return getMoviesLce.execute(GetMoviesLce.Params.setPage(page)).map(new Function<Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                if (MovieListViewModel.this.linkedHashMapLce == null)
                    MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce;
                else
                    MovieListViewModel.this.linkedHashMapLce.getData().putAll(linkedHashMapLce.getData());
                return MovieListViewModel.this.linkedHashMapLce;
            }
        });*/

        /*return paginator.concatMap(new Function<Integer, Flowable<Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Flowable<Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Integer page) throws Exception {
                return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));
            }
        }).doOnNext(linkedHashMapLce1 -> MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce1);*/
    }


    void setLinkedHashMapLce(Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) {
        this.linkedHashMapLce = linkedHashMapLce;
        lcePublishProcessor.onNext(this.linkedHashMapLce);
    }

    void setFavorite(String movieId, boolean isFavorite) {
        Log.d(TAG, "setFavorite = " + movieId + " : " + isFavorite);
        linkedHashMapLce.getData().get(movieId).setFavorite(isFavorite);
        lcePublishProcessor.onNext(this.linkedHashMapLce);
    }

    Flowable<Lce<LinkedHashMap<String, Movie>>> listenForChangesInMovieList() {
        return lcePublishProcessor;
    }
}
