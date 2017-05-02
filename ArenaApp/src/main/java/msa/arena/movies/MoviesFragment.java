package msa.arena.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Lazy;
import msa.arena.R;
import msa.arena.base.BaseEpoxyAdapter;
import msa.arena.base.BaseFragment;
import msa.arena.injector.components.MovieComponent;
import msa.arena.utilities.EndlessRecyclerViewScrollListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends BaseFragment implements MoviesView {

    @BindView(R.id.text_page_number)
    TextView textView_Page;

    @BindView(R.id.recycler_movies)
    RecyclerView recyclerView_Movies;

    @Inject
    Lazy<MoviesPresenter> moviesPresenterLazy;

    private BaseEpoxyAdapter baseEpoxyAdapter;

    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    private boolean shouldEnableEndlessScroll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
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

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                textView_Page.setText(String.valueOf(page));
                moviesPresenterLazy.get().onLoadMore(page);
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(MovieComponent.class).inject(this);
        moviesPresenterLazy.get().setMoviesView(this);
        moviesPresenterLazy.get().initializePresenter();
        shouldEnableEndlessScroll = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (shouldEnableEndlessScroll)
            recyclerView_Movies.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        moviesPresenterLazy.get().onStop();
        recyclerView_Movies.removeOnScrollListener(endlessRecyclerViewScrollListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        moviesPresenterLazy.get().onDestroy();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadMovieItem(MoviesItem moviesItem) {
        baseEpoxyAdapter.addItem(moviesItem);
    }

    @Override
    public void onComplete() {
        shouldEnableEndlessScroll = false;
        recyclerView_Movies.removeOnScrollListener(endlessRecyclerViewScrollListener);
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

    @OnClick(R.id.btn_complete)
    void onClick() {
        moviesPresenterLazy.get().callComplete();
    }
}
