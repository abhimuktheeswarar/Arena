package msa.arena.movies;

import java.util.List;

import msa.arena.base.BaseView;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public interface MoviesView extends BaseView {

    void loadMovieItem(MoviesItem moviesItem);

    void loadMovieItem(List<MoviesItem> moviesItemList);

    void clearMovieItems();

    void onComplete();
}
