package msa.rehearsal.round2.subround2_1;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msa.domain.entities.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import msa.rehearsal.R;
import msa.rehearsal.base.BaseFragment;
import msa.rehearsal.injector.components.MovieComponent;
import msa.rehearsal.utilities.EndlessRecyclerViewScrollListener;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

public class SubRound2_1Fragment extends BaseFragment {

    @BindView(R.id.recyclerView_r2s1)
    RecyclerView recyclerView;

    @Inject
    Lazy<SubRound2_1ViewModel> subRound2_1ViewModelLazy;
    List<Movie> movieList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MovieEpoxyController movieEpoxyController;
    private MovieTypedEpoxyController movieTypedEpoxyController;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private CompositeSubscription compositeSubscription;
    private CompositeDisposable compositeDisposable;

    public static SubRound2_1Fragment newInstance() {
        return new SubRound2_1Fragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent(MovieComponent.class).inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_round2_subround1, container, false);
        ButterKnife.bind(this, rootView);
        setupViews();
        return rootView;
    }


    private void setupViews() {

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager);

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);


        movieEpoxyController = new MovieEpoxyController();

        movieTypedEpoxyController = new MovieTypedEpoxyController();


        recyclerView.setAdapter(movieTypedEpoxyController.getAdapter());
        //movieEpoxyController.requestModelBuild();

    }

    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    @Override
    public void onPause() {
        super.onPause();
        unBind();
    }

    private void bind() {

        compositeSubscription = new CompositeSubscription();

        compositeDisposable = new CompositeDisposable();

        /*compositeDisposable.add(subRound2_1ViewModelLazy.get().getMovies(0).subscribe(new Consumer<List<Movie>>() {
            @Override
            public void accept(@NonNull List<Movie> movies) throws Exception {
                movieEpoxyController.setMovies(movies);
                movieEpoxyController.requestModelBuild();
            }
        }));*/


      /*compositeDisposable.add(endlessRecyclerViewScrollListener.getScrollState().subscribeOn(io.reactivex.schedulers.Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).switchMap(new Function<EndlessRecyclerViewScrollListener.ScrollState, Observable<Movie>>() {
            @Override
            public Observable<Movie> apply(@NonNull EndlessRecyclerViewScrollListener.ScrollState scrollState) throws Exception {
                Log.d(SubRound2_1Fragment.class.getSimpleName(), "Page = " + scrollState.getPage());
                return subRound2_1ViewModelLazy.get().getMovieFeed(scrollState.getPage());
            }
        }).scan(new ArrayList<>(), new BiFunction<ArrayList<Movie>, Movie, ArrayList<Movie>>() {
            @Override
            public ArrayList<Movie> apply(@NonNull ArrayList<Movie> array, @NonNull Movie item) throws Exception {
                array.add(item);
                return array;
            }
        }).subscribe(new Consumer<ArrayList<Movie>>() {
            @Override
            public void accept(@NonNull ArrayList<Movie> movies) throws Exception {
                movieTypedEpoxyController.setData(movies);
            }
        }));*/

        compositeDisposable.add(endlessRecyclerViewScrollListener.getScrollState().subscribeOn(io.reactivex.schedulers.Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).switchMap(new Function<EndlessRecyclerViewScrollListener.ScrollState, Observable<List<Movie>>>() {
            @Override
            public Observable<List<Movie>> apply(@NonNull EndlessRecyclerViewScrollListener.ScrollState scrollState) throws Exception {
                Log.d(SubRound2_1Fragment.class.getSimpleName(), "Page = " + scrollState.getPage());
                return subRound2_1ViewModelLazy.get().getMovies(scrollState.getPage());
            }
        }).scan(new BiFunction<List<Movie>, List<Movie>, List<Movie>>() {
            @Override
            public List<Movie> apply(@NonNull List<Movie> movies, @NonNull List<Movie> movies2) throws Exception {
                movies.addAll(movies2);
                return movies;
            }
        }).subscribe(new Consumer<List<Movie>>() {
            @Override
            public void accept(@NonNull List<Movie> movies) throws Exception {
                movieTypedEpoxyController.setData(movies);
            }
        }));




        compositeDisposable.add(movieEpoxyController.getSelectedMovie().subscribeOn(io.reactivex.schedulers.Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Movie>() {
            @Override
            public void accept(@NonNull Movie movie) throws Exception {
                showToastMessage(movie.getMovieName());
            }
        }));

    }

    private void unBind() {
        compositeSubscription.unsubscribe();
        compositeDisposable.dispose();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.removeOnScrollListener(endlessRecyclerViewScrollListener);
    }
}
