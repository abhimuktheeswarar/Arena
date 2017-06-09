package msa.arena.data.repository.datasources.local.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.msa.domain.entities.Movie;
import com.msa.domain.entities.User;

import org.reactivestreams.Subscription;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.processors.ReplayProcessor;
import msa.arena.data.repository.BaseDataSource;

/**
 * Created by Abhimuktheeswarar on 09-06-2017.
 */

public class SharedPreferenceDataSource implements BaseDataSource {


    private final String TAG = SharedPreferenceDataSource.class.getSimpleName();
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final RxSharedPreferences rxSharedPreferences;
    private final Observable<User> userObservable;
    private final PublishProcessor<User> publishProcessor;
    private final ReplayProcessor<User> userReplayProcessor;

    private final String USER_ID = "userId";
    private final String DISPLAY_NAME = "displayName";

    public SharedPreferenceDataSource(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
        userObservable = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> e) throws Exception {


            }
        });

        publishProcessor = PublishProcessor.create();
        publishProcessor.doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(@NonNull Subscription subscription) throws Exception {
                //Log.d(TAG, "doOnSubscribe called");
                publishProcessor.onNext(getUserData());
            }
        });

        userReplayProcessor = ReplayProcessor.create();
        userReplayProcessor.onNext(getUserData());


        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(USER_ID) || key.equals(DISPLAY_NAME)) {
                    userReplayProcessor.onNext(getUserData());
                }
            }
        });
    }

    private User getUserData() {
        return new User(sharedPreferences.getString(USER_ID, "id_"), sharedPreferences.getString(DISPLAY_NAME, "NA"));
    }


    @Override
    public Observable<User> getUser() {
        return userReplayProcessor.toObservable();
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
