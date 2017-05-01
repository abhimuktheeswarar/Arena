package msa.arena.movies;

import com.msa.domain.entities.Movie;
import com.msa.domain.interactor.DefaultObserver;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class MoviesObserver extends DefaultObserver<Movie> {

    private final MoviesView moviesView;

    MoviesObserver(MoviesView moviesView) {
        this.moviesView = moviesView;
    }

    @Override
    public void onNext(Movie movie) {
        super.onNext(movie);
        moviesView.loadMovieItem(new MoviesItem_().movieId(movie.getMovieId()).movieName(movie.getMovieName()));
    }

    @Override
    public void onComplete() {
        super.onComplete();
    }

    @Override
    public void onError(Throwable exception) {
        super.onError(exception);
        moviesView.onError(exception.getMessage());
    }
}
