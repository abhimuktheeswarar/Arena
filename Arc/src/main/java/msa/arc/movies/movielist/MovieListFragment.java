package msa.arc.movies.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.davidmoten.rx2.util.Pair;
import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DisposableSubscriber;
import msa.arc.R;
import msa.arc.base.BaseFragment;
import msa.arc.utilities.EndlessRecyclerViewScrollListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends BaseFragment {

    private static final String TAG = MovieListFragment.class.getSimpleName();

    @BindView(R.id.rV_movies)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;

    private EndlessRecyclerViewScrollListener scrollListener;

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
        //linearLayoutManager.setRecycleChildrenOnDetach(true);
        movieListController = new MovieListController();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(movieListController.getAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieListViewModel = getViewModel(MovieListViewModel.class);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager, movieListViewModel.getPage()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                movieListViewModel.loadMore();
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        bind();
    }


    private void bind() {

        recyclerView.addOnScrollListener(scrollListener);


        compositeDisposable.add(listenForInternetConnectivity().delay(1, TimeUnit.SECONDS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean && !movieListViewModel.isInitialized()) {
                    Log.d(TAG, "retrying");
                    movieListViewModel.loadMore();
                } else Log.d(TAG, "Not retrying");
            }
        }));


        DisposableSubscriber<Lce<LinkedHashMap<String, Movie>>> disposableSubscriber = new DisposableSubscriber<Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public void onNext(Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) {
                Log.d(MovieListFragment.class.getSimpleName(), "data received");
                if (!linkedHashMapLce.hasError()) movieListController.setMovies(linkedHashMapLce);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        compositeDisposable.add(disposableSubscriber);

        movieListViewModel.getMovieList().subscribe(disposableSubscriber);


        compositeDisposable.add(movieListController.listenForFavorite().subscribe(new Consumer<Pair<String, Boolean>>() {
            @Override
            public void accept(@NonNull Pair<String, Boolean> stringBooleanPair) throws Exception {
                movieListViewModel.setFavorite(stringBooleanPair.left(), stringBooleanPair.right());
            }
        }));


    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerView.removeOnScrollListener(scrollListener);
        compositeDisposable.clear();
        compositeSubscription.clear();
    }
}
