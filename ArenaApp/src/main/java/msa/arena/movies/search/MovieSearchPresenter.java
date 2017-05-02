package msa.arena.movies.search;

import com.msa.domain.entities.Movie;
import com.msa.domain.usecases.SearchMovie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import msa.arena.base.BasePresenterInterface;
import msa.arena.injector.PerActivity;
import msa.arena.movies.MoviesItem_;
import msa.arena.movies.MoviesView;

/**
 * Created by Abhimuktheeswarar on 02-05-2017.
 */

@PerActivity
class MovieSearchPresenter implements BasePresenterInterface {

    private final SearchMovie searchMovie;

    private MoviesView moviesView;

    @Inject
    MovieSearchPresenter(SearchMovie searchMovie) {
        this.searchMovie = searchMovie;
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

    }

    @Override
    public void onPause() {

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
                for (Movie movie : movies)
                    moviesView.loadMovieItem(new MoviesItem_().movieId(movie.getMovieId()).movieName(movie.getMovieName()));

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
}
