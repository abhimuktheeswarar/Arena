package msa.arc.movies.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.davidmoten.rx2.util.Pair;
import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        movieListController = new MovieListController();
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                movieListViewModel.loadMore();
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(movieListController.getAdapter());


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

        recyclerView.addOnScrollListener(scrollListener);

       /* compositeDisposable.add(movieListViewModel.getMovieList().subscribe(linkedHashMapLce -> {
            Log.d(MovieListFragment.class.getSimpleName(), "Page = " + linkedHashMapLce.getData().size());
            movieListViewModel.setLinkedHashMapLce(linkedHashMapLce);

        }));*/

        compositeDisposable.add(movieListViewModel.getMovieList2().subscribe(new Consumer<Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public void accept(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                movieListController.setMovies(linkedHashMapLce);
            }
        }));



       /*compositeDisposable.add(movieListViewModel.getMovieList().subscribe(new Consumer<Lce<LinkedHashMap<String, Movie>>>() {
           @Override
           public void accept(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
               movieListViewModel.setLinkedHashMapLce(linkedHashMapLce);
           }
       }));

        compositeDisposable.add(movieListViewModel.listenForChangesInMovieList().subscribe(new Consumer<Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public void accept(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                Log.d(TAG, "movie list updated");
                movieListController.setMovies(linkedHashMapLce);
            }
        }));*/

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
    }
}
