package msa.arena.data.repository.datasources.remote;

import android.content.Context;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.entities.User;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import msa.arena.data.entities.remote.MovieSearchResult;
import msa.arena.data.entities.remote.list.MovieListPojo;
import msa.arena.data.entities.remote.list.MovieListResult;
import msa.arena.data.repository.BaseDataSource;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class RemoteDataSource<T> implements BaseDataSource {

    private static final String TAG = RemoteDataSource.class.getSimpleName();

    private final ArenaApi arenaApi;
    private final Context context;

    public RemoteDataSource(ArenaApi arenaApi, Context context) {
        this.arenaApi = arenaApi;
        this.context = context;
        ReactiveNetwork.observeNetworkConnectivity(context).subscribe(new Consumer<Connectivity>() {
            @Override
            public void accept(@NonNull Connectivity connectivity) throws Exception {
                Log.d(TAG, "Is network available 0 = " + connectivity.isAvailable());
            }
        });
    }

    @Override
    public Observable<User> getUser() {
        return null;
    }

    @Override
    public Completable updateUser(User user) {
        return null;
    }

    @Override
    public Observable<Movie> getMovies(int page) {

        getObs(arenaApi.getMovies());

        return arenaApi.getMovies().flatMap(new Function<MovieListPojo, ObservableSource<Movie>>() {
            @Override
            public ObservableSource<Movie> apply(@NonNull MovieListPojo movieSearchPojo) throws Exception {
                List<Movie> movies = new ArrayList<Movie>();
                for (MovieListResult movieSearchResult : movieSearchPojo.getResults())
                    movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
                return Observable.fromIterable(movies);
            }
        });
    }

    private <V> Observable<V> getObs(Observable<V> observable) {
        return ReactiveNetwork.observeNetworkConnectivity(context).switchMap(new Function<Connectivity, ObservableSource<? extends V>>() {
            @Override
            public ObservableSource<? extends V> apply(@NonNull Connectivity connectivity) throws Exception {
                if (connectivity.isAvailable()) return observable;
                else return Observable.error(new Throwable("no internet"));
            }
        });
    }

    private <V> Flowable<V> getFlow(Flowable<V> flowable) {
        return ReactiveNetwork.observeNetworkConnectivity(context).toFlowable(BackpressureStrategy.BUFFER).switchMap(new Function<Connectivity, Publisher<? extends V>>() {
            @Override
            public Publisher<? extends V> apply(@NonNull Connectivity connectivity) throws Exception {
                Log.d(TAG, "Is network available 1 = " + connectivity.isAvailable());
                if (connectivity.isAvailable()) return flowable;
                else return Flowable.empty();
            }
        });
    }


    private <V> Flowable<V> getFlow2(Flowable<V> flowable) {
        return ReactiveNetwork.observeNetworkConnectivity(context).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                Log.v(TAG, "repeatWhen, call");
                /**
                 * This is called only once.
                 * 5 means each repeated call will be delayed by 5 seconds
                 */
                return objectObservable.delay(5, TimeUnit.SECONDS);
            }
        }).takeUntil(new Predicate<Connectivity>() {
            @Override
            public boolean test(@NonNull Connectivity connectivity) throws Exception {
                return connectivity.isAvailable();
            }
        }).filter(new Predicate<Connectivity>() {
            @Override
            public boolean test(@NonNull Connectivity connectivity) throws Exception {
                return connectivity.isAvailable();
            }
        }).toFlowable(BackpressureStrategy.BUFFER).flatMap(new Function<Connectivity, Publisher<V>>() {
            @Override
            public Publisher<V> apply(@NonNull Connectivity connectivity) throws Exception {
                return flowable;
            }
        });
    }

    private <V> Flowable<V> getFlow3(Flowable<V> flowable) {
        return ReactiveNetwork.observeNetworkConnectivity(context).toFlowable(BackpressureStrategy.BUFFER).filter(new Predicate<Connectivity>() {
            @Override
            public boolean test(@NonNull Connectivity connectivity) throws Exception {
                return connectivity.isAvailable();
            }
        }).flatMap(new Function<Connectivity, Publisher<V>>() {
            @Override
            public Publisher<V> apply(@NonNull Connectivity connectivity) throws Exception {
                return flowable;
            }
        });

    }


    @Override
    public Observable<List<Movie>> getMovieList(int page) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getMovieList2(int page) {
        return null;
    }

    @Override
    public Observable<LinkedHashMap<String, Movie>> getMovieHashes(int page) {
        return null;
    }

    @Override
    public Flowable<List<Movie>> getMovieFlow(int page) {
        return null;
    }

    @Override
    public Flowable<Movie> getMoviesTypeTwo(int page) {
        return getFlow3(arenaApi.getMoviesTypeTwo()).flatMap(new Function<MovieListPojo, Publisher<Movie>>() {
            @Override
            public Publisher<Movie> apply(@NonNull MovieListPojo movieListPojo) throws Exception {
                List<Movie> movies = new ArrayList<Movie>();
                for (MovieListResult movieSearchResult : movieListPojo.getResults())
                    movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
                return Flowable.fromIterable(movies);
            }
        });
    }

    @Override
    public Flowable<Lce<Movie>> getMoviesTypeTwoLce(int page) {
        return arenaApi.getMoviesTypeTwo().flatMap(new Function<MovieListPojo, Publisher<Lce<Movie>>>() {
            @Override
            public Publisher<Lce<Movie>> apply(@NonNull MovieListPojo movieListPojo) throws Exception {
                List<Lce<Movie>> movies = new ArrayList<>();
                for (MovieListResult movieSearchResult : movieListPojo.getResults())
                    movies.add(Lce.data(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false)));
                if (page < 3) return Flowable.fromIterable(movies);
                else return Flowable.just(Lce.error(new Throwable("Test error for Lce")));
            }
        });
    }

    @Override
    public Observable<List<Movie>> searchMovie(String query) {
        Log.d(TAG, "searchMovie = " + query);
        return arenaApi.searchMovie(query).map(movieSearchPojo -> {
            List<Movie> movies = new ArrayList<Movie>();
            for (MovieSearchResult movieSearchResult : movieSearchPojo.getResults())
                movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
            return movies;
        });
    }

    @Override
    public Single<List<Movie>> searchForMovie(String query) {
        Log.d(TAG, "searchForMovie = " + query);
        return arenaApi.searchForMovie(query).map(movieSearchPojo -> {
            List<Movie> movies = new ArrayList<Movie>();
            for (MovieSearchResult movieSearchResult : movieSearchPojo.getResults())
                movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
            return movies;
        });
        /*return arenaApi.getMedicineSuggestions(new SearchSubmit(query)).map(new Function<List<SearchMedResult>, List<Movie>>() {
            @Override
            public List<Movie> apply(@NonNull List<SearchMedResult> searchMedResults) throws Exception {
                List<Movie> movies = new ArrayList<Movie>();
                for (SearchMedResult medResult : searchMedResults)
                    movies.add(new Movie(String.valueOf(medResult.getId()), medResult.getTabletName()));
                return movies;
            }
        });*/
    }
}
