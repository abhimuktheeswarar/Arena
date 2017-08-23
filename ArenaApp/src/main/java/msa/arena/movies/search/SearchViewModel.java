package msa.arena.movies.search;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import msa.arena.base.BaseViewModel;
import msa.domain.entities.Movie;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.holder.datastate.DataState;
import msa.domain.holder.datastate.DataStateContainer;
import msa.domain.usecases.SearchMovies;

/**
 * Created by Abhimuktheeswarar on 22-08-2017.
 */

public class SearchViewModel extends BaseViewModel {

    private final SearchMovies searchMovies;

    private DataStateContainer<LinkedHashMap<String, Movie>> dataStateContainer;

    private PublishSubject<String> querySubject;

    private ReplaySubject<DataStateContainer<LinkedHashMap<String, Movie>>> dataStateContainerReplaySubject;

    @Inject
    SearchViewModel(SearchMovies searchMovies) {
        this.searchMovies = searchMovies;
        initializeViewModel();
    }

    @Override
    protected void initializeViewModel() {
        super.initializeViewModel();
        dataStateContainer = new DataStateContainer<>();
        querySubject = PublishSubject.create();
        dataStateContainerReplaySubject = ReplaySubject.create();

        querySubject.concatMap(new Function<String, Observable<ResourceCarrier<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Observable<ResourceCarrier<LinkedHashMap<String, Movie>>> apply(@NonNull String query) throws Exception {
                return searchMovies.execute(SearchMovies.Params.setQuery(query)).toObservable();
            }
        }).observeOn(Schedulers.computation()).map(linkedHashMapResourceCarrier -> {
            Log.d(TAG, "Status = " + linkedHashMapResourceCarrier.status);
            switch (linkedHashMapResourceCarrier.status) {
                case SUCCESS:
                    dataStateContainer.setDataState(DataState.SUCCESS);
                    dataStateContainer.setData(linkedHashMapResourceCarrier.data);
                    break;
                case COMPLETED:
                    dataStateContainer.setDataState(DataState.COMPLETED);
                    dataStateContainer.setData(linkedHashMapResourceCarrier.data);
                    break;
                case ERROR:
                    dataStateContainer.setDataState(DataState.ERROR);
                    dataStateContainer.setData(null);
                    dataStateContainer.setMessage(linkedHashMapResourceCarrier.message);
                    break;
            }
            return dataStateContainer;
        }).startWith(dataStateContainer).subscribe(dataStateContainerReplaySubject);
    }

    public void searchIt(String query) {
        dataStateContainer.setDataState(DataState.LOADING);
        dataStateContainerReplaySubject.onNext(dataStateContainer);
        querySubject.onNext(query);
    }

    public Observable<DataStateContainer<LinkedHashMap<String, Movie>>> getMovieSearchObserver() {
        return dataStateContainerReplaySubject;
    }

    public Observable<DataStateContainer<List<Movie>>> getMovieListSearchObserver() {
        return dataStateContainerReplaySubject.map(linkedHashMapDataStateContainer -> {
            DataStateContainer<List<Movie>> stateContainer = new DataStateContainer<>();
            stateContainer.setDataState(linkedHashMapDataStateContainer.getDataState());
            List<Movie> movies = new ArrayList<>();
            if (linkedHashMapDataStateContainer.getData() != null) {
                for (Map.Entry<String, Movie> entry : linkedHashMapDataStateContainer.getData().entrySet()) {
                    Movie movie = entry.getValue();
                    movies.add(movie);
                }
            }
            stateContainer.setData(movies);
            return stateContainer;
        });
    }

    public Movie getMovieByIndex(int index) {
        List<Movie> movies = new ArrayList<>(dataStateContainer.getData().values());
        return movies.get(index);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        querySubject.onComplete();
        dataStateContainerReplaySubject.onComplete();
    }
}
