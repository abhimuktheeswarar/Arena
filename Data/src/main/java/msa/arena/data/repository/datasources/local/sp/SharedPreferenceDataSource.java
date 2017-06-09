package msa.arena.data.repository.datasources.local.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.msa.domain.entities.Movie;
import com.msa.domain.entities.User;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import msa.arena.data.repository.BaseDataSource;

/**
 * Created by Abhimuktheeswarar on 09-06-2017.
 */

public class SharedPreferenceDataSource implements BaseDataSource {


    private final String TAG = SharedPreferenceDataSource.class.getSimpleName();
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final Observable<User> userObservable;

    private final String USER_ID = "userId";
    private final String DISPLAY_NAME = "displayName";

    public SharedPreferenceDataSource(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        userObservable = Observable.create(e -> {
            SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = (sharedPreferences1, key) -> {
                if (key.equals(USER_ID) || key.equals(DISPLAY_NAME)) {
                    e.onNext(getUserData());
                }
            };
            if (!e.isDisposed()) {
                e.onNext(getUserData());
                sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            } else {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            }

        });
    }

    private User getUserData() {
        return new User(sharedPreferences.getString(USER_ID, "id_"), sharedPreferences.getString(DISPLAY_NAME, "NA"));
    }


    @Override
    public Observable<User> getUser() {
        return userObservable;
    }

    @Override
    public Completable updateUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, user.getUserId()).apply();
        editor.putString(DISPLAY_NAME, user.getDisplayName());
        if (editor.commit()) return Completable.complete();
        else return Completable.error(new Throwable("Error updating user"));
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
