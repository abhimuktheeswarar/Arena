package msa.arena.movies.search;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.github.davidmoten.rx2.RetryWhen;
import com.msa.domain.entities.Movie;
import com.msa.domain.usecases.SearchMovie;
import com.msa.domain.usecases.SearchMovieTypeTwo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import msa.arena.base.BasePresenterInterface;
import msa.arena.injector.PerActivity;
import msa.arena.movies.MoviesItem;
import msa.arena.movies.MoviesItem_;
import msa.arena.movies.MoviesView;

/**
 * Created by Abhimuktheeswarar on 02-05-2017.
 */

@PerActivity
class MovieSearchPresenter implements BasePresenterInterface {

    private static final String TAG = MovieSearchPresenter.class.getSimpleName();


    private final SearchMovie searchMovie;
    private final SearchMovieTypeTwo searchMovieTypeTwo;
    PublishSubject<String> subject = PublishSubject.create();
    Flowable<Long> delays = Flowable.just(10L, 20L, 30L, 30L, 30L);
    String[] countries = {"India", "Sweden", "Austria", "France", "Germany", "Canada", "Mexico", "Brazil", "Chile"};
    private MoviesView moviesView;
    private Disposable disposable;

    @Inject
    MovieSearchPresenter(SearchMovie searchMovie, SearchMovieTypeTwo searchMovieTypeTwo) {
        this.searchMovie = searchMovie;
        this.searchMovieTypeTwo = searchMovieTypeTwo;
    }

    void setMoviesView(MoviesView moviesView) {
        this.moviesView = moviesView;
    }

    @Override
    public void initializePresenter() {

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
        onSubscribe();

    }

    @Override
    public void onPause() {
        unSubscribe();

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    void onSearch(String query) {
        searchMovie.execute(new DisposableObserver<List<Movie>>() {
            @Override
            public void onNext(@NonNull List<Movie> movies) {
                List<MoviesItem> moviesItems = new ArrayList<MoviesItem>();
                for (Movie movie : movies)
                    moviesItems.add(new MoviesItem_().movieId(movie.getMovieId()).movieName(movie.getMovieName()));
                moviesView.loadMovieItem(moviesItems);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                moviesView.onError(e.getMessage());

            }

            @Override
            public void onComplete() {
                dispose();

            }
        }, query);

    }

    private void onSubscribe() {
        if (disposable == null) {
            initDisposable();
        }
    }

    private void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }

    }


    private void initDisposable() {
        //noinspection unchecked
        disposable = subject
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<List<Movie>>>() {
                    @Override
                    public ObservableSource<List<Movie>> apply(String s) throws Exception {
                        Log.d(TAG, "getting books for " + s);
                        return searchMovieTypeTwo.execute(s);
                    }
                }).toFlowable(BackpressureStrategy.BUFFER)
                .retryWhen(RetryWhen.retryWhenInstanceOf(NetworkErrorException.class).build())
                .retryWhen(RetryWhen.delays(delays, TimeUnit.SECONDS).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        onMoviesFetched(movies);
                    }
                });
    }

    private void onMoviesFetched(List<Movie> movies) {
        moviesView.loadMovies(movies);
        List<MoviesItem> moviesItems = new ArrayList<MoviesItem>();
        for (Movie movie : movies)
            moviesItems.add(new MoviesItem_().movieId(movie.getMovieId()).movieName(movie.getMovieName()));
        moviesView.loadMovieItem(moviesItems);

    }

    void onSearchTypeTwo(String newText) {
        if (newText == null || newText.length() == 0) {
            moviesView.clearMovieItems();
        } else {
            subject.onNext(newText);
        }

    }


}
