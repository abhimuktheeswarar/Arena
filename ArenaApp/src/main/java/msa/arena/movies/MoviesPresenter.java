package msa.arena.movies;

import com.msa.domain.entities.Movie;
import com.msa.domain.usecases.GetMoviesTypeOne;
import com.msa.domain.usecases.GetMoviesTypeTwo;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import msa.arena.base.BasePresenterInterface;
import msa.arena.injector.PerActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

@PerActivity
class MoviesPresenter implements BasePresenterInterface {

    private final GetMoviesTypeOne getMoviesTypeOne;
    private final GetMoviesTypeTwo getMoviesTypeTwo;

    private MoviesView moviesView;

    private Disposable disposable;

    private PublishProcessor<Integer> paginator;

    @Inject
    MoviesPresenter(GetMoviesTypeOne getMoviesTypeOne, GetMoviesTypeTwo getMoviesTypeTwo) {
        this.getMoviesTypeOne = getMoviesTypeOne;
        this.getMoviesTypeTwo = getMoviesTypeTwo;
    }

    @Override
    public void initializePresenter() {
        paginator = PublishProcessor.create();
        setupSubscriber();
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

    }

    @Override
    public void onDestroy() {
        getMoviesTypeOne.dispose();
    }

    void setMoviesView(MoviesView moviesView) {
        this.moviesView = moviesView;
    }

    private void setupSubscriber() {
        paginator.onBackpressureDrop().concatMap(new Function<Integer, Publisher<Movie>>() {
            @Override
            public Publisher<Movie> apply(@NonNull Integer integer) throws Exception {
                return getMoviesTypeTwo.execute(integer);
            }
        }).subscribe(new Consumer<Movie>() {
            @Override
            public void accept(@NonNull Movie movie) throws Exception {
                moviesView.loadMovieItem(new MoviesItem_().movieId(movie.getMovieId()).movieName(movie.getMovieName()));
            }
        });
    }

    void onLoadMore(int page) {
        paginator.onNext(page);
    }
}
