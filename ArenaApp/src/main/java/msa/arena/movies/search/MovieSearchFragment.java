package msa.arena.movies.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import msa.arena.R;
import msa.arena.base.BaseEpoxyAdapter;
import msa.arena.base.BaseFragment;
import msa.arena.injector.components.MovieComponent;
import msa.arena.movies.MoviesItem;
import msa.arena.movies.MoviesView;
import msa.arena.utilities.RxSearch;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieSearchFragment extends BaseFragment implements MoviesView {

    @BindView(R.id.recycler_movies)
    RecyclerView recyclerView_Movies;

    SearchView searchView;

    @Inject
    Lazy<MovieSearchPresenter> movieSearchPresenterLazy;

    private BaseEpoxyAdapter baseEpoxyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_search, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_Movies.setLayoutManager(linearLayoutManager);
        baseEpoxyAdapter = new BaseEpoxyAdapter();
        recyclerView_Movies.setAdapter(baseEpoxyAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(MovieComponent.class).inject(this);
        movieSearchPresenterLazy.get().setMoviesView(this);
        movieSearchPresenterLazy.get().initializePresenter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
    }


    @Override
    public void onResume() {
        super.onResume();
        RxSearch.fromSearchView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(item -> item.length() > 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> movieSearchPresenterLazy.get().onSearch(query));

    }

    @Override
    public void loadMovieItem(MoviesItem moviesItem) {
        baseEpoxyAdapter.addItem(moviesItem);
    }

    @Override
    public void loadMovieItem(List<MoviesItem> moviesItemList) {
        baseEpoxyAdapter.removeAllItems();
        baseEpoxyAdapter.addItem(moviesItemList);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(String message) {
        showToastMessage(message);
    }

    @Override
    public void hideError() {

    }

    @Override
    public Context context() {
        return getActivity();
    }
}
