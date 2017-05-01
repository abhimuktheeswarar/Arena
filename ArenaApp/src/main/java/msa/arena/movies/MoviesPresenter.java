package msa.arena.movies;

import com.msa.domain.usecases.GetMoviesTypeOne;

import javax.inject.Inject;

import msa.arena.base.BasePresenterInterface;
import msa.arena.injector.PerActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

@PerActivity
class MoviesPresenter implements BasePresenterInterface {

    private final GetMoviesTypeOne getMoviesTypeOne;

    private MoviesView moviesView;

    @Inject
    MoviesPresenter(GetMoviesTypeOne getMoviesTypeOne) {
        this.getMoviesTypeOne = getMoviesTypeOne;
    }

    @Override
    public void initializePresenter() {
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

    void onLoadMore(int page) {
        getMoviesTypeOne.execute(new MoviesObserver(moviesView), page);
    }
}
