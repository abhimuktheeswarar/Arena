package msa.arena.movies;

import android.util.Log;

import com.msa.domain.entities.Movie;
import com.msa.domain.usecases.GetMoviesTypeOne;
import com.msa.domain.usecases.GetMoviesTypeThree;
import com.msa.domain.usecases.GetMoviesTypeTwo;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import msa.arena.base.BasePresenterInterface;
import msa.arena.injector.PerActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

@PerActivity
class MoviesPresenter implements BasePresenterInterface {

    private final String TAG = MoviesPresenter.class.getSimpleName();

    private final GetMoviesTypeOne getMoviesTypeOne;
    private final GetMoviesTypeTwo getMoviesTypeTwo;
    private final GetMoviesTypeThree getMoviesTypeThree;
    private MoviesView moviesView;
    private Disposable disposable;
    private PublishProcessor<Integer> paginator;
    private MoviesObserver movieObserver;
    private DisposableSubscriber<Movie> disMovSubs;

    @Inject
    MoviesPresenter(GetMoviesTypeOne getMoviesTypeOne, GetMoviesTypeTwo getMoviesTypeTwo, GetMoviesTypeThree getMoviesTypeThree) {
        this.getMoviesTypeOne = getMoviesTypeOne;
        this.getMoviesTypeTwo = getMoviesTypeTwo;
        this.getMoviesTypeThree = getMoviesTypeThree;
    }

    @Override
    public void initializePresenter() {
        paginator = PublishProcessor.create();
        setupSubscriberTypeTwo();
        onLoadMore(0);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        disMovSubs.dispose();
    }

    @Override
    public void onDestroy() {
        getMoviesTypeOne.dispose();
    }

    void setMoviesView(MoviesView moviesView) {
        this.moviesView = moviesView;
    }

    private void setupSubscriberTypeTwo() {

        disMovSubs = new DisposableSubscriber<Movie>() {
            @Override
            public void onNext(Movie movie) {
                if (movie.getMovieId() != null)
                    moviesView.loadMovieItem(new MoviesItem_().movieId(movie.getMovieId()).movieName(movie.getMovieName()));
                else {
                    Log.d(TAG, "Movie is empty");
                    paginator.onComplete();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                moviesView.onError(throwable.getMessage());
                Log.e(TAG, throwable.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");

            }
        };

        paginator.concatMap(new Function<Integer, Publisher<Movie>>() {
            @Override
            public Publisher<Movie> apply(@NonNull Integer integer) throws Exception {
                return getMoviesTypeThree.execute(integer).toFlowable(BackpressureStrategy.BUFFER);
            }
        }).subscribe(disMovSubs);

    }

    @Deprecated
    private void setupSubscriber() {

        paginator.onBackpressureDrop().concatMap(new Function<Integer, Publisher<Movie>>() {
            @Override
            public Publisher<Movie> apply(@NonNull Integer integer) throws Exception {
                return getMoviesTypeTwo.execute(integer);
            }
        }).subscribe(new Consumer<Movie>() {
            @Override
            public void accept(@NonNull Movie movie) throws Exception {
                if (movie != null)
                    moviesView.loadMovieItem(new MoviesItem_().movieId(movie.getMovieId()).movieName(movie.getMovieName()));
                else {
                    Log.d(TAG, "Movie is empty");
                    paginator.onComplete();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                moviesView.onError(throwable.getMessage());
                Log.e(TAG, throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.d(TAG, "onComplete");
            }
        });
    }

    void onLoadMore(int page) {
        paginator.onNext(page);
    }

    void callComplete() {
        paginator.onComplete();

    }
}
