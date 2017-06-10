package msa.arc.movies.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import msa.arc.R;
import msa.arc.base.BaseFragment;
import msa.arc.utilities.EndlessRecyclerViewScrollListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends BaseFragment {

    @BindView(R.id.rV_movies)
    RecyclerView recyclerView;


    private EndlessRecyclerViewScrollListener scrollListener;

    private LinearLayoutManager linearLayoutManager;

    private MovieListViewModel movieListViewModel;

    private MovieListController movieListController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getContext());
        movieListController = new MovieListController();
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(movieListController.getAdapter());
        recyclerView.addOnScrollListener(scrollListener);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieListViewModel = getViewModel(MovieListViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        bind();
    }


    private void bind() {

        compositeDisposable.add(scrollListener.getScrollState().toFlowable(BackpressureStrategy.BUFFER).concatMap(new Function<EndlessRecyclerViewScrollListener.ScrollState, Flowable<Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Flowable<Lce<LinkedHashMap<String, Movie>>> apply(@NonNull EndlessRecyclerViewScrollListener.ScrollState scrollState) throws Exception {
                return movieListViewModel.getMovieList(scrollState.getPage());
            }
        }).subscribe(linkedHashMapLce -> movieListController.setMovies(linkedHashMapLce)));

    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerView.removeOnScrollListener(scrollListener);
    }
}
