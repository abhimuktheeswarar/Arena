package msa.arena.data.repository.datasources.local.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.msa.domain.entities.Movie;
import com.msa.domain.entities.User;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import msa.arena.data.repository.BaseDataSource;

/**
 * Created by Abhimuktheeswarar on 09-06-2017.
 */

public class SharedPreferenceDataSource implements BaseDataSource {


    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final RxSharedPreferences rxSharedPreferences;
    private final Observable<User> userObservable;

    public SharedPreferenceDataSource(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
        userObservable = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> e) throws Exception {


            }
        });
    }


    @Override
    public Observable<User> getUser() {
        return Observable.zip(rxSharedPreferences.getString("userId", "empty").asObservable(), rxSharedPreferences.getString("displayName", "notSet").asObservable(), new BiFunction<String, String, User>() {
            @Override
            public User apply(@NonNull String userId, @NonNull String displayName) throws Exception {
                return new User(userId, displayName);
            }
        });
    }

    @Override
    public Completable updateUser(User user) {
        //rxSharedPreferences.getString("").asConsumer(user.getDisplayName());
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
    public Observable<List<Movie>> searchMovie(String query) {
        return null;
    }

    @Override
    public Single<List<Movie>> searchForMovie(String query) {
        return null;
    }
}
