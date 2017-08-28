package msa.arc.movies.movielist;

import android.util.Log;

import org.reactivestreams.Publisher;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.processors.ReplayProcessor;
import msa.arc.base.BaseViewModel;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;
import msa.domain.usecases.old.GetMoviesLce;

/**
 * Created by Abhimuktheeswarar on 11-06-2017.
 */

public class MovieListViewModel extends BaseViewModel {

    private static final String TAG = MovieListViewModel.class.getSimpleName();
    private final GetMoviesLce getMoviesLce;

    private Lce<LinkedHashMap<String, Movie>> linkedHashMapLce;

    private ReplayProcessor<Lce<LinkedHashMap<String, Movie>>> lceReplayProcessor;

    private PublishProcessor<Integer> paginator;

    private int page;

    private boolean isInitialized;

    @Inject
    MovieListViewModel(GetMoviesLce getMoviesLce) {
        this.getMoviesLce = getMoviesLce;
        paginator = PublishProcessor.create();
        linkedHashMapLce = Lce.loading();
        lceReplayProcessor = ReplayProcessor.create();
        page = 1;

        paginator.startWith(page).concatMap(new Function<Integer, Publisher<? extends Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Publisher<? extends Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Integer page) throws Exception {
                return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));
            }
        }).map(new Function<Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                if (!linkedHashMapLce.hasError()) {
                    if (linkedHashMapLce.getData() != null) isInitialized = true;
                    if (MovieListViewModel.this.linkedHashMapLce.getData() == null)
                        MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce;
                    else
                        MovieListViewModel.this.linkedHashMapLce.getData().putAll(linkedHashMapLce.getData());
                    return MovieListViewModel.this.linkedHashMapLce;
                } else {
                    isInitialized = false;
                    return linkedHashMapLce;
                }

            }
        }).subscribe(lceReplayProcessor);
    }

    Flowable<Lce<LinkedHashMap<String, Movie>>> getMovieList() {
        return lceReplayProcessor;
    }

    void setFavorite(String movieId, boolean isFavorite) {
        Log.d(TAG, "setFavorite = " + movieId + " : " + isFavorite);
        this.linkedHashMapLce.getData().get(movieId).setFavorite(isFavorite);
        lceReplayProcessor.onNext(this.linkedHashMapLce);
    }

    void loadMore() {
        page++;
        paginator.onNext(page);
    }

    int getPage() {
        return page;
    }

    boolean isInitialized() {
        return isInitialized;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        paginator.onComplete();
        lceReplayProcessor.onComplete();
    }


}
