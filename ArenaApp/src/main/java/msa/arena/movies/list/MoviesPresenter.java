package msa.arena.movies.list;

import android.util.Log;

import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.usecases.GetMoviesTypeOne;
import com.msa.domain.usecases.GetMoviesTypeTwo;
import com.msa.domain.usecases.GetMoviesTypeTwoLce;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import msa.arena.base.BasePresenterInterface;
import msa.arena.injector.PerActivity;
import msa.arena.movies.MoviesItem_;
import msa.arena.movies.MoviesView;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@PerActivity
class MoviesPresenter implements BasePresenterInterface {

    private final String TAG = MoviesPresenter.class.getSimpleName();

    private final GetMoviesTypeOne getMoviesTypeOne;
    private final GetMoviesTypeTwo getMoviesTypeTwo;
    private final GetMoviesTypeTwoLce getMoviesTypeTwoLce;
    private MoviesView moviesView;
    private PublishProcessor<Integer> paginator;
    private DisposableSubscriber<Movie> disMovSubs;
    private DisposableSubscriber<Lce<Movie>> disposableSubscriber;

    @Inject
    MoviesPresenter(GetMoviesTypeOne getMoviesTypeOne, GetMoviesTypeTwo getMoviesTypeTwo, GetMoviesTypeTwoLce getMoviesTypeTwoLce) {
        this.getMoviesTypeOne = getMoviesTypeOne;
        this.getMoviesTypeTwo = getMoviesTypeTwo;
        this.getMoviesTypeTwoLce = getMoviesTypeTwoLce;
    }

    @Override
    public void initializePresenter() {
        paginator = PublishProcessor.create();
        //setupSubscriberTypeTwo();
        setupSubscriberTypeTwoLce();
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
        //disMovSubs.dispose();
        disposableSubscriber.dispose();
    }

    @Override
    public void onDestroy() {
        getMoviesTypeOne.dispose();
    }

    void setMoviesView(MoviesView moviesView) {
        this.moviesView = moviesView;
    }

    private void setupSubscriberTypeTwo() {

        disMovSubs =
                new DisposableSubscriber<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        if (movie.getMovieId() != null) {
                            moviesView.loadMovieItem(
                                    new MoviesItem_().movieId(movie.getMovieId()).movieName(movie.getMovieName()));
                        } else {
                            Log.d(TAG, "Movie is empty");
                            paginator.onComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        moviesView.onError(throwable.getMessage());
                        Log.e(TAG, throwable.getMessage() != null ? throwable.getMessage() : "Some error");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                };

        paginator
                .concatMap(
                        new Function<Integer, Publisher<Movie>>() {
                            @Override
                            public Publisher<Movie> apply(@NonNull Integer integer) throws Exception {
                                return getMoviesTypeTwo.execute(integer);
                            }
                        })
                .subscribe(disMovSubs);
    }

    private void setupSubscriberTypeTwoLce() {

        disposableSubscriber = new DisposableSubscriber<Lce<Movie>>() {
            @Override
            public void onNext(Lce<Movie> movieLce) {
                if (movieLce.isLoading()) {
                    Log.d(TAG, "Loading...");

                } else if (movieLce.hasError()) {
                    Log.e(TAG, "Error found -> " + movieLce.getError().getMessage());
                    movieLce.getError();
                } else {
                    Log.d(TAG, "Movies received");
                    movieLce.getData();
                    if (movieLce.getData().getMovieId() != null) {
                        moviesView.loadMovieItem(
                                new MoviesItem_().movieId(movieLce.getData().getMovieId()).movieName(movieLce.getData().getMovieName()));
                    } else {
                        Log.d(TAG, "Movie is empty");
                        paginator.onComplete();
                    }
                }

            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, t.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");

            }
        };

        paginator
                .concatMap(
                        new Function<Integer, Publisher<Lce<Movie>>>() {
                            @Override
                            public Publisher<Lce<Movie>> apply(@NonNull Integer integer) throws Exception {
                                return getMoviesTypeTwoLce.execute(integer);
                            }
                        })
                .subscribe(disposableSubscriber);
    }

    @Deprecated
    private void setupSubscriber() {

        paginator
                .onBackpressureDrop()
                .concatMap(
                        new Function<Integer, Publisher<Movie>>() {
                            @Override
                            public Publisher<Movie> apply(@NonNull Integer integer) throws Exception {
                                return getMoviesTypeTwo.execute(integer);
                            }
                        })
                .subscribe(
                        new Consumer<Movie>() {
                            @Override
                            public void accept(@NonNull Movie movie) throws Exception {
                                if (movie != null) {
                                    moviesView.loadMovieItem(
                                            new MoviesItem_()
                                                    .movieId(movie.getMovieId())
                                                    .movieName(movie.getMovieName()));
                                } else {
                                    Log.d(TAG, "Movie is empty");
                                    paginator.onComplete();
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                moviesView.onError(throwable.getMessage());
                                Log.e(TAG, throwable.getMessage());
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.d(TAG, "onComplete");
                            }
                        });
    }

    void onLoadMore(int page) {
        Log.d(TAG, "onLoadMore = " + page);
        paginator.onNext(page);
    }

    void callComplete() {
        paginator.onComplete();
    }
}
