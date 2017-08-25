package msa.data.repository.datasources.remote;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import org.reactivestreams.Publisher;

import java.net.UnknownHostException;
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
import msa.data.entities.remote.MovieSearchPojo;
import msa.data.entities.remote.MovieSearchResult;
import msa.data.entities.remote.list.MovieListPojo;
import msa.data.entities.remote.list.MovieListResult;
import msa.data.repository.BaseDataSource;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;
import msa.domain.entities.User;
import msa.domain.holder.carrier.ResourceCarrier;
import retrofit2.HttpException;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class RemoteDataSource implements BaseDataSource {

    private static final String TAG = RemoteDataSource.class.getSimpleName();

    private final ArenaApi arenaApi;
    private final Context context;
    private boolean isInternetAvailable;

    public RemoteDataSource(ArenaApi arenaApi, Context context) {
        this.arenaApi = arenaApi;
        this.context = context;
        ReactiveNetwork.observeNetworkConnectivity(context).subscribe(new Consumer<Connectivity>() {
            @Override
            public void accept(@NonNull Connectivity connectivity) throws Exception {
                isInternetAvailable = connectivity.isAvailable();
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
    public Observable<Movie> getMovies1(int page) {

        getObs(arenaApi.getMovies1());

        return arenaApi.getMovies1().flatMap(new Function<MovieListPojo, ObservableSource<Movie>>() {
            @Override
            public ObservableSource<Movie> apply(@NonNull MovieListPojo movieSearchPojo) throws Exception {
                List<Movie> movies = new ArrayList<Movie>();
                for (MovieListResult movieSearchResult : movieSearchPojo.getResults())
                    movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
                return Observable.fromIterable(movies);
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
        return getFlow3(arenaApi.getMovies(page)).flatMap(new Function<MovieListPojo, Publisher<Movie>>() {
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

        return arenaApi.getMovies(page).flatMap(new Function<MovieListPojo, Publisher<Lce<Movie>>>() {
            @Override
            public Publisher<Lce<Movie>> apply(@NonNull MovieListPojo movieListPojo) throws Exception {
                Log.d(TAG, "Loading movies...,,,");
                List<Lce<Movie>> movies = new ArrayList<>();
                int i = 0;
                Log.d(TAG, "Loading movie size = " + movieListPojo.getResults().size());
                for (MovieListResult movieSearchResult : movieListPojo.getResults()) {
                    Log.d(TAG, "Loading movie = " + i);
                    i++;
                    movies.add(Lce.data(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false)));
                }
                return Flowable.fromIterable(movies);
            }
        }).onErrorReturn(new Function<Throwable, Lce<Movie>>() {
            @Override
            public Lce<Movie> apply(@NonNull Throwable throwable) throws Exception {
                if (throwable instanceof UnknownHostException || throwable instanceof HttpException)
                    return Lce.error(new Throwable("No network dude"));
                else return Lce.error(new Throwable("No network dude 2"));
            }
        }).startWith(Lce.loading());

        /*return ReactiveNetwork.observeNetworkConnectivity(context).toFlowable(BackpressureStrategy.BUFFER).map(new Function<Connectivity, Boolean>() {
            @Override
            public Boolean apply(@NonNull Connectivity connectivity) throws Exception {
                return connectivity.isAvailable();
            }
        }).switchMap(new Function<Boolean, Publisher<? extends Lce<Movie>>>() {
            @Override
            public Publisher<? extends Lce<Movie>> apply(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean)
                    return arenaApi.getMovies().flatMap(new Function<MovieListPojo, Publisher<Lce<Movie>>>() {
                        @Override
                        public Publisher<Lce<Movie>> apply(@NonNull MovieListPojo movieListPojo) throws Exception {
                            Log.d(TAG, "Loading movies...,,,");
                            List<Lce<Movie>> movies = new ArrayList<>();
                            int i = 0;
                            Log.d(TAG, "Loading movie size = " + movieListPojo.getResults().size());
                            for (MovieListResult movieSearchResult : movieListPojo.getResults()) {
                                Log.d(TAG, "Loading movie = " + i);
                                i++;
                                movies.add(Lce.data(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false)));
                            }
                            return Flowable.fromIterable(movies);
                        }
                    });
                else return Flowable.just(Lce.error(new Throwable("No network")));
            }
        }).startWith(Lce.loading());*/


        /*return ReactiveNetwork.observeNetworkConnectivity(context).map(new Function<Connectivity, Boolean>() {
            @Override
            public Boolean apply(@NonNull Connectivity connectivity) throws Exception {
                return connectivity.isAvailable();
            }
        }).switchMap(new Function<Boolean, ObservableSource<Lce<Movie>>>() {
            @Override
            public ObservableSource<Lce<Movie>> apply(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean)
                    return arenaApi.getMovies().toObservable().flatMap(new Function<MovieListPojo, ObservableSource<Lce<Movie>>>() {
                        @Override
                        public ObservableSource<Lce<Movie>> apply(@NonNull MovieListPojo movieListPojo) throws Exception {
                            Log.d(TAG, "Loading movies...,,,");
                            List<Lce<Movie>> movies = new ArrayList<>();
                            int i = 0;
                            Log.d(TAG, "Loading movie size = " + movieListPojo.getResults().size());
                            for (MovieListResult movieSearchResult : movieListPojo.getResults()) {
                                Log.d(TAG, "Loading movie = " + i);
                                i++;
                                movies.add(Lce.data(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false)));
                            }
                            return Observable.fromIterable(movies);
                        }
                    });
                else return Observable.just(Lce.error(new Throwable("No network")));
            }
        }).startWith(Lce.loading()).toFlowable(BackpressureStrategy.BUFFER);*/

        /*return arenaApi.getMovies().flatMap(new Function<MovieListPojo, Publisher<Lce<Movie>>>() {
            @Override
            public Publisher<Lce<Movie>> apply(@NonNull MovieListPojo movieListPojo) throws Exception {
                Log.d(TAG, "Loading movies...,,,");
                List<Lce<Movie>> movies = new ArrayList<>();
                int i = 0;
                Log.d(TAG, "Loading movie size = " + movieListPojo.getResults().size());
                for (MovieListResult movieSearchResult : movieListPojo.getResults()) {
                    Log.d(TAG, "Loading movie = " + i);
                    i++;
                    movies.add(Lce.data(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false)));
                }
                return Flowable.fromIterable(movies);
            }
        }).retryWhen(new RetryWithDelay(5, 1000));*/

        /*return ReactiveNetwork.observeNetworkConnectivity(context).toFlowable(BackpressureStrategy.BUFFER).switchMap(new Function<Connectivity, Publisher<Lce<Movie>>>() {
            @Override
            public Publisher<Lce<Movie>> apply(@NonNull Connectivity connectivity) throws Exception {
                if (connectivity.isAvailable())
                    return Flowable.just(Lce.error(new Throwable("No network")));
                else
                    return arenaApi.getMovies().flatMap(new Function<MovieListPojo, Publisher<Lce<Movie>>>() {
                        @Override
                        public Publisher<Lce<Movie>> apply(@NonNull MovieListPojo movieListPojo) throws Exception {
                            Log.d(TAG, "Loading movies...,,,");
                            List<Lce<Movie>> movies = new ArrayList<>();
                            int i = 0;
                            Log.d(TAG, "Loading movie size = " + movieListPojo.getResults().size());
                            for (MovieListResult movieSearchResult : movieListPojo.getResults()) {
                                Log.d(TAG, "Loading movie = " + i);
                                i++;
                                movies.add(Lce.data(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false)));
                            }
                            return Flowable.fromIterable(movies);
                        }
                    });
            }
        });*/
       /* return getFlow6(arenaApi.getMovies()).flatMap(new Function<Lce<MovieListPojo>, Publisher<Lce<Movie>>>() {
            @Override
            public Publisher<Lce<Movie>> apply(@NonNull Lce<MovieListPojo> movieListPojoLce) throws Exception {
                Log.d(TAG, "Loading movies...,,,");
                List<Lce<Movie>> movies = new ArrayList<>();
                int i = 0;
                Log.d(TAG, "Loading movie size = " + movieListPojoLce.getData().getResults().size());
                for (MovieListResult movieSearchResult : movieListPojoLce.getData().getResults()) {
                    Log.d(TAG, "Loading movie = " + i);
                    i++;
                    movies.add(Lce.data(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false)));
                }
                return Flowable.fromIterable(movies);
            }
        });*/
    }

    @Override
    public Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLce(int page) {
        return arenaApi.getMovies(page).map(movieListPojo -> {
            LinkedHashMap<String, Movie> movieLinkedHashMap = new LinkedHashMap<String, Movie>();
            for (MovieListResult movieSearchResult : movieListPojo.getResults()) {
                movieLinkedHashMap.put(String.valueOf(movieSearchResult.getId()), new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
            }
            return Lce.data(movieLinkedHashMap);
        }).onErrorResumeNext(new Function<Throwable, Publisher<? extends Lce<LinkedHashMap<String, Movie>>>>() {
            @Override
            public Publisher<? extends Lce<LinkedHashMap<String, Movie>>> apply(@NonNull Throwable throwable) throws Exception {
                return Flowable.just(Lce.error(throwable));
            }
        });
    }

    @Override
    public Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLceR(int page) {
        return null;
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
        return arenaApi.searchForMovie(query).map(new Function<MovieSearchPojo, List<Movie>>() {
            @Override
            public List<Movie> apply(@NonNull MovieSearchPojo movieSearchPojo) throws Exception {
                List<Movie> movies = new ArrayList<Movie>();
                for (MovieSearchResult movieSearchResult : movieSearchPojo.getResults())
                    movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
                return movies;
            }
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

    @Override
    public Completable setFavoriteMovie(String movieId, boolean isFavorite) {
        return null;
    }

    @Override
    public Single<ResourceCarrier<LinkedHashMap<String, Movie>>> searchMovies(String query) {
        Log.d(TAG, "Query = " + query);
        return arenaApi.searchForMovie(query).map(new Function<MovieSearchPojo, ResourceCarrier<LinkedHashMap<String, Movie>>>() {
            @Override
            public ResourceCarrier<LinkedHashMap<String, Movie>> apply(@NonNull MovieSearchPojo movieSearchPojo) throws Exception {
                LinkedHashMap<String, Movie> linkedHashMap = new LinkedHashMap<>();
                for (MovieSearchResult movieSearchResult : movieSearchPojo.getResults())
                    linkedHashMap.put(String.valueOf(movieSearchResult.getId()), new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
                if (linkedHashMap.size() > 0) return ResourceCarrier.success(linkedHashMap);
                else
                    return ResourceCarrier.error("Sorry, we couldn't find anything", 2, linkedHashMap);
            }
        }).onErrorReturn(new Function<Throwable, ResourceCarrier<LinkedHashMap<String, Movie>>>() {
            @Override
            public ResourceCarrier<LinkedHashMap<String, Movie>> apply(@NonNull Throwable throwable) throws Exception {
                if (throwable instanceof UnknownHostException || throwable instanceof NetworkErrorException)
                    return ResourceCarrier.error("Network not available");
                else return ResourceCarrier.error(throwable.getMessage());
            }
        });
    }

    @Override
    public Observable<ResourceCarrier<LinkedHashMap<String, Movie>>> searchMoviesObservable(String query) {
        return arenaApi.searchForMovieObservable(query).map(movieSearchPojo -> {
            LinkedHashMap<String, Movie> linkedHashMap = new LinkedHashMap<>();
            for (MovieSearchResult movieSearchResult : movieSearchPojo.getResults())
                linkedHashMap.put(String.valueOf(movieSearchResult.getId()), new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle(), false));
            if (linkedHashMap.size() > 0) return ResourceCarrier.success(linkedHashMap);
            else
                return ResourceCarrier.error("Sorry, we couldn't find anything", 2, linkedHashMap);
        }).onErrorReturn(throwable -> {
            if (throwable instanceof UnknownHostException || throwable instanceof NetworkErrorException)
                return ResourceCarrier.error("Network not available");
            else return ResourceCarrier.error(throwable.getMessage());
        });
    }

    @Override
    public Flowable<ResourceCarrier<LinkedHashMap<String, Movie>>> getMovies(int page) {
        Log.d(TAG, "getMovies = " + page);
        return arenaApi.getMovies(page).map(movieListPojo -> {
            LinkedHashMap<String, Movie> linkedHashMap = new LinkedHashMap<>();
            for (MovieListResult movieListResult : movieListPojo.getResults())
                linkedHashMap.put(String.valueOf(movieListResult.getId()), new Movie(String.valueOf(movieListResult.getId()), movieListResult.getTitle(), false));
            if (linkedHashMap.size() > 0) return ResourceCarrier.loading(linkedHashMap);
            else
                return ResourceCarrier.error("Sorry, error loading movies", 1, linkedHashMap);
        }).onErrorReturn(throwable -> {
            if (throwable instanceof UnknownHostException || throwable instanceof NetworkErrorException)
                return ResourceCarrier.error("Network not available");
            else return ResourceCarrier.error(throwable.getMessage());
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

    private <V> Flowable<Lce<V>> getFlow4(Flowable<V> flowable) {
        return ReactiveNetwork.observeNetworkConnectivity(context).toFlowable(BackpressureStrategy.BUFFER).switchMap(new Function<Connectivity, Publisher<? extends Lce<V>>>() {
            @Override
            public Publisher<? extends Lce<V>> apply(@NonNull Connectivity connectivity) throws Exception {
                Log.d(TAG, "Is network available 1 = " + connectivity.isAvailable());
                if (connectivity.isAvailable()) return null;
                return Flowable.just(Lce.error(new Throwable("No network")));
            }
        });

    }


    private <V> Flowable<Lce<V>> getFlow5(Flowable<V> flowable) {
        return ReactiveNetwork.observeNetworkConnectivity(context).toFlowable(BackpressureStrategy.BUFFER).switchMap(new Function<Connectivity, Publisher<? extends Lce<V>>>() {
            @Override
            public Publisher<? extends Lce<V>> apply(@NonNull Connectivity connectivity) throws Exception {
                Log.d(TAG, "Is network available 1 = " + connectivity.isAvailable());
                if (connectivity.isAvailable()) {
                    Log.d(TAG, "Is network available 2 oh yeah = " + connectivity.isAvailable());
                    return flowable.map(new Function<V, Lce<V>>() {
                        @Override
                        public Lce<V> apply(@NonNull V v) throws Exception {
                            return Lce.data(v);
                        }
                    });
                } else {
                    Log.d(TAG, "Is network available 3 nooo = " + connectivity.isAvailable());
                    return Flowable.just(Lce.error(new Throwable("No network")));
                }
            }
        });
    }

    private <V> Flowable<Lce<V>> getFlow6(Flowable<V> flowable) {
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
        }).toFlowable(BackpressureStrategy.BUFFER).flatMap(new Function<Connectivity, Publisher<Lce<V>>>() {
            @Override
            public Publisher<Lce<V>> apply(@NonNull Connectivity connectivity) throws Exception {
                Log.d(TAG, "Is network available 1 = " + connectivity.isAvailable());
                if (connectivity.isAvailable()) return flowable.map(new Function<V, Lce<V>>() {
                    @Override
                    public Lce<V> apply(@NonNull V v) throws Exception {
                        return Lce.data(v);
                    }
                });
                else return Flowable.just(Lce.error(new Throwable("No network")));
            }
        });
    }


}
