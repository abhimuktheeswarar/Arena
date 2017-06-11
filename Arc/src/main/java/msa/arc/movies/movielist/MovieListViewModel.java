package msa.arc.movies.movielist;

import android.util.Log;

import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.usecases.GetMoviesLce;

import org.reactivestreams.Publisher;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.processors.ReplayProcessor;
import msa.arc.base.BaseViewModel;

/**
 * Created by Abhimuktheeswarar on 11-06-2017.
 */

public class MovieListViewModel extends BaseViewModel {

    private static final String TAG = MovieListViewModel.class.getSimpleName();
    private final GetMoviesLce getMoviesLce;

    private Lce<LinkedHashMap<String, Movie>> linkedHashMapLce;

    private PublishProcessor<Lce<LinkedHashMap<String, Movie>>> lcePublishProcessor;

    private ReplayProcessor<Lce<LinkedHashMap<String, Movie>>> lceReplayProcessor;

    private PublishProcessor<Integer> paginator;

    private ReplayProcessor<Integer> replayProcessor;

    private int page;

    @Inject
    public MovieListViewModel(GetMoviesLce getMoviesLce) {
        this.getMoviesLce = getMoviesLce;
        lcePublishProcessor = PublishProcessor.create();
        paginator = PublishProcessor.create();
        linkedHashMapLce = Lce.loading();

        replayProcessor = ReplayProcessor.create();
        replayProcessor.onNext(1);

        lceReplayProcessor = ReplayProcessor.create();

        page = 1;


       /* paginator.startWith(1).concatMap(new Function<Integer, Publisher<? extends Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Publisher<? extends Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Integer page) throws Exception {
                return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));
            }
        }).scan(new BiFunction<Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce1, @NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce2) throws Exception {
                MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce1;
                MovieListViewModel.this.linkedHashMapLce.getData().putAll(linkedHashMapLce2.getData());
                return MovieListViewModel.this.linkedHashMapLce;
            }
        }).filter(new Predicate<Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public boolean test(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                return linkedHashMapLce.getData()!=null;
            }
        }).subscribe(lceReplayProcessor);*/

        paginator.startWith(1).concatMap(new Function<Integer, Publisher<? extends Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Publisher<? extends Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Integer page) throws Exception {
                return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));
            }
        }).map(new Function<Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                if (MovieListViewModel.this.linkedHashMapLce.getData() == null)
                    MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce;
                else
                    MovieListViewModel.this.linkedHashMapLce.getData().putAll(linkedHashMapLce.getData());
                return MovieListViewModel.this.linkedHashMapLce;
            }
        }).subscribe(lceReplayProcessor);


    }

    Flowable<Lce<LinkedHashMap<String, Movie>>> getMovieList2() {

        return lceReplayProcessor;
    }

    Flowable<Lce<LinkedHashMap<String, Movie>>> getMovieList() {
        /*return getMoviesLce.execute(GetMoviesLce.Params.setPage(page)).scan((o1, o2) -> {
            LinkedHashMap<String, Movie> previous = o1.getData();
            previous.putAll(o2.getData());
            Lce<LinkedHashMap<String, Movie>> total = Lce.data(previous);
            Log.d(TAG, "o1 size = " + o1.getData().size());
            Log.d(TAG, "o2 size = " + o1.getData().size());
            Log.d(TAG, "new size = " + total.getData().size());
            setLinkedHashMapLce(total);
            return total;
        });*/

        /*return paginator.concatMap(new Function<Integer, Publisher<? extends Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Publisher<? extends Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Integer page) throws Exception {
                return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));
            }
        }).scan((o1, o2) -> {
            LinkedHashMap<String, Movie> previous = o1.getData();
            previous.putAll(o2.getData());
            Lce<LinkedHashMap<String, Movie>> total = Lce.data(previous);
            Log.d(TAG, "o1 size = " + o1.getData().size());
            Log.d(TAG, "o2 size = " + o1.getData().size());
            Log.d(TAG, "new size = " + total.getData().size());
            setLinkedHashMapLce(total);
            return total;
        });*/


        return paginator.concatMap(new Function<Integer, Publisher<? extends Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Publisher<? extends Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Integer page) throws Exception {
                return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));
            }
        });


        //WORKING
        /*return paginator.concatMap(new Function<Integer, Publisher<? extends Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Publisher<? extends Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Integer page) throws Exception {
                return getMoviesLce.execute(GetMoviesLce.Params.setPage(page));
            }
        }).map(new Function<Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                if (MovieListViewModel.this.linkedHashMapLce == null)
                    MovieListViewModel.this.linkedHashMapLce = linkedHashMapLce;
                else
                    MovieListViewModel.this.linkedHashMapLce.getData().putAll(linkedHashMapLce.getData());
                return MovieListViewModel.this.linkedHashMapLce;
            }
        }).filter(new Predicate<Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public boolean test(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                return linkedHashMapLce.getData() != null;
            }
        });*/

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
        if (this.linkedHashMapLce.getData() == null) this.linkedHashMapLce = linkedHashMapLce;
        else this.linkedHashMapLce.getData().putAll(linkedHashMapLce.getData());
        lcePublishProcessor.onNext(this.linkedHashMapLce);
    }

    void setFavorite(String movieId, boolean isFavorite) {
        Log.d(TAG, "setFavorite = " + movieId + " : " + isFavorite);
        this.linkedHashMapLce.getData().get(movieId).setFavorite(isFavorite);
        lceReplayProcessor.onNext(this.linkedHashMapLce);
    }

    Flowable<Lce<LinkedHashMap<String, Movie>>> listenForChangesInMovieList() {
        return lcePublishProcessor;
    }


    void loadMore() {
        page++;
        paginator.onNext(page);
    }

    public int getPage() {
        return page;
    }
}
