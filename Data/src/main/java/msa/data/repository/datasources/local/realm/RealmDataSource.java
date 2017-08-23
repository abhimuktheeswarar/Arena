package msa.data.repository.datasources.local.realm;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import msa.data.repository.BaseDataSource;
import msa.data.repository.datasources.local.realm.realmobjects.MovieR;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;
import msa.domain.entities.User;
import msa.domain.holder.carrier.ResourceCarrier;

/**
 * Created by Abhimuktheeswarar on 12-06-2017.
 */

public class RealmDataSource implements BaseDataSource {

    private static final String TAG = RealmDataSource.class.getSimpleName();

    private final Realm realm;

    public RealmDataSource(Realm realm) {
        this.realm = realm;
        List<MovieR> movieRs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            movieRs.add(new MovieR(UUID.randomUUID().toString(), "MovieName_" + i, 2017));
        }
        realm.beginTransaction();
        realm.copyToRealm(movieRs);
        realm.commitTransaction();
        Log.d(TAG, "RealmDataSource put dummy data");
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
        return null;
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
        return null;
    }

    @Override
    public Flowable<Lce<Movie>> getMoviesTypeTwoLce(int page) {
        return null;
    }

    @Override
    public Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLce(int page) {
        return null;
    }

    @Override
    public Flowable<Lce<LinkedHashMap<String, Movie>>> getMoviesLceR(int page) {
        Log.d(TAG, "getMoviesLceR page = " + page);
        RealmResults<MovieR> movieRs = realm.where(MovieR.class).findAll();
        Log.d(TAG, "getMoviesLceR size ->  " + movieRs.size());
        return Observable.create(new ObservableOnSubscribe<Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Lce<LinkedHashMap<String, Movie>>> e) throws Exception {

                final RealmChangeListener<RealmResults<MovieR>> r = new RealmChangeListener<RealmResults<MovieR>>() {
                    @Override
                    public void onChange(RealmResults<MovieR> movieRs) {
                        Log.d(TAG, "getMoviesLceR size = " + movieRs.size());
                        LinkedHashMap<String, Movie> movies = new LinkedHashMap<String, Movie>();
                        for (MovieR movieR : movieRs) {
                            movies.put(movieR.getMovieId(), new Movie(movieR.getMovieId(), movieR.getMovieName(), false));
                        }
                        if (!e.isDisposed()) e.onNext(Lce.data(movies));

                    }
                };


                movieRs.addChangeListener(r);

            }
        }).toFlowable(BackpressureStrategy.BUFFER);
       /* return RxJavaInterop.toV2Observable(movieRs.asObservable()).toFlowable(BackpressureStrategy.BUFFER).map(new Function<RealmResults<MovieR>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull RealmResults<MovieR> movieRs2) throws Exception {
                Log.d(TAG, "getMoviesLceR size = " + movieRs2.size());
                LinkedHashMap<String, Movie> movies = new LinkedHashMap<String, Movie>();
                for (MovieR movieR : movieRs2) {
                    movies.put(movieR.getMovieId(), new Movie(movieR.getMovieId(), movieR.getMovieName(), false));
                }
                return Lce.data(movies);
            }
        });*/
    }

    @Override
    public Observable<List<Movie>> searchMovie(String query) {
        return null;
    }

    @Override
    public Single<List<Movie>> searchForMovie(String query) {
        return null;
    }

    @Override
    public Completable setFavoriteMovie(String movieId, boolean isFavorite) {
        Log.d(TAG, "setFavoriteMovie");
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new MovieR(movieId, isFavorite));
        realm.commitTransaction();
        return Completable.complete();
    }

    @Override
    public Single<ResourceCarrier<LinkedHashMap<String, Movie>>> searchMovies(String query) {
        return null;
    }

    @Override
    public Observable<ResourceCarrier<LinkedHashMap<String, Movie>>> searchMoviesObservable(String query) {
        return null;
    }
}
