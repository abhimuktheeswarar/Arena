package msa.arena.movies.list;

import msa.arena.base.BaseView;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

interface MoviesView extends BaseView {

    void loadMovieItem(MoviesItem moviesItem);

    void onComplete();
}
