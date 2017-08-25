package msa.arena.movies.list.listold;

import java.util.List;

import msa.arena.base.old.BaseView;
import msa.domain.entities.Movie;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public interface MoviesView extends BaseView {

    void loadMovies(List<Movie> movies);

    void loadMovieItem(MoviesItem moviesItem);

    void loadMovieItem(List<MoviesItem> moviesItemList);

    void clearMovieItems();

    void onComplete();
}
