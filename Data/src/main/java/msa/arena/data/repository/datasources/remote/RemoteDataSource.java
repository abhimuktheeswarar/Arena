package msa.arena.data.repository.datasources.remote;

import android.util.Log;

import com.msa.domain.entities.Movie;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import msa.arena.data.entities.remote.MovieSearchPojo;
import msa.arena.data.entities.remote.MovieSearchResult;
import msa.arena.data.entities.remote.list.MovieListPojo;
import msa.arena.data.entities.remote.list.MovieListResult;
import msa.arena.data.repository.BaseDataSource;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class RemoteDataSource implements BaseDataSource {

    private static final String TAG = RemoteDataSource.class.getSimpleName();

    private final ArenaApi arenaApi;

    public RemoteDataSource(ArenaApi arenaApi) {
        this.arenaApi = arenaApi;
    }

    @Override
    public Observable<Movie> getMovies(int page) {
        return arenaApi.getMovies().flatMap(new Function<MovieListPojo, ObservableSource<Movie>>() {
            @Override
            public ObservableSource<Movie> apply(@NonNull MovieListPojo movieSearchPojo) throws Exception {
                List<Movie> movies = new ArrayList<Movie>();
                for (MovieListResult movieSearchResult : movieSearchPojo.getResults())
                    movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle()));
                return Observable.fromIterable(movies);
            }
        });
    }

    @Override
    public Flowable<Movie> getMoviesTypeTwo(int page) {
        return arenaApi.getMoviesTypeTwo().flatMap(new Function<MovieListPojo, Publisher<Movie>>() {
            @Override
            public Publisher<Movie> apply(@NonNull MovieListPojo movieListPojo) throws Exception {
                List<Movie> movies = new ArrayList<Movie>();
                for (MovieListResult movieSearchResult : movieListPojo.getResults())
                    movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle()));
                return Flowable.fromIterable(movies);
            }
        });
    }

    @Override
    public Observable<List<Movie>> searchMovie(String query) {
        Log.d(TAG, "searchMovie = " + query);
        return arenaApi.searchMovie(query).map(movieSearchPojo -> {
            List<Movie> movies = new ArrayList<Movie>();
            for (MovieSearchResult movieSearchResult : movieSearchPojo.getResults())
                movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle()));
            return movies;
        });
    }

    @Override
    public Single<List<Movie>> searchForMovie(String query) {
        Log.d(TAG, "searchForMovie = " + query);
        return arenaApi.searchForMovie(query).map(movieSearchPojo -> {
            List<Movie> movies = new ArrayList<Movie>();
            for (MovieSearchResult movieSearchResult : movieSearchPojo.getResults())
                movies.add(new Movie(String.valueOf(movieSearchResult.getId()), movieSearchResult.getTitle()));
            return movies;
        });
    }
}
