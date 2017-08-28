package msa.arena.movies;

import com.msa.domain.entities.Movie;

import java.util.List;

import msa.arena.base.BaseView;

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
