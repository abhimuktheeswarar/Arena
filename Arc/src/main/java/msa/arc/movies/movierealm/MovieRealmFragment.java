package msa.arc.movies.movierealm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import msa.arc.R;
import msa.data.repository.datasources.local.realm.realmobjects.MovieR;
import msa.domain.entities.Movie;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Abhimuktheeswarar on 12-06-2017.
 */

public class MovieRealmFragment extends Fragment {

    private static final String TAG = MovieRealmFragment.class.getSimpleName();

    Realm realm;

    RealmResults<MovieR> movieRs;

    @BindViews({R.id.edit_movie_name, R.id.edit_movie_releaseYear})
    List<EditText> editTexts;

    MovieR movieR;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_realm, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.button_query_movie)
    void query() {
        Log.d(TAG, "MovieRs size = " + movieRs.size());

        if (movieRs.size() > 0) {
            for (MovieR movieR : movieRs) {
                Log.d(TAG, movieR.getMovieName() + " | " + movieR.getReleaseYear());
            }
        }
    }

    @OnClick(R.id.button_add_movie)
    void addMovieSimple() {

        String movieName = editTexts.get(0).getText() != null ? editTexts.get(0).getText().toString() : "MovieName+" + System.currentTimeMillis();
        int releaseYear = editTexts.get(1).getText() != null ? Integer.valueOf(editTexts.get(1).getText().toString()) : 2017;

        movieR = new MovieR(UUID.randomUUID().toString(), movieName, releaseYear);

        // Persist your data in a transaction
        realm.beginTransaction();
        // Persist unmanaged objects
        realm.copyToRealm(movieR);
        realm.commitTransaction();


    }

    @OnClick(R.id.button_update_movie)
    void updateMovieSimple() {

        String movieName = editTexts.get(0).getText() != null ? editTexts.get(0).getText().toString() : "MovieName+" + System.currentTimeMillis();
        int releaseYear = editTexts.get(1).getText() != null ? Integer.valueOf(editTexts.get(1).getText().toString()) : 2017;

        if (movieR != null) {
            movieR.setMovieName(movieName);
            movieR.setReleaseYear(releaseYear);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(movieR);
            realm.commitTransaction();
        } else Toast.makeText(getContext(), "Error updating", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onStart() {
        super.onStart();
        movieRs = realm.where(MovieR.class).equalTo("releaseYear", 2017).findAll();
        movieRs.addChangeListener(new RealmChangeListener<RealmResults<MovieR>>() {
            @Override
            public void onChange(RealmResults<MovieR> movieRs) {
                for (MovieR movieR : movieRs) {
                    //Log.d(TAG, movieR.getMovieName() + " || " + movieR.getReleaseYear());
                }
            }
        });

        movieRs.asObservable().map(new Func1<RealmResults<MovieR>, List<Movie>>() {
            @Override
            public List<Movie> call(RealmResults<MovieR> movieRs) {
                List<Movie> movies = new ArrayList<Movie>();
                for (MovieR movieR : movieRs) {
                    Log.d(TAG, movieR.getMovieName() + " ||| " + movieR.getReleaseYear());
                    movies.add(new Movie(movieR.getMovieId(), movieR.getMovieName(), false));
                }
                return movies;
            }
        }).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                for (Movie movie : movies) {
                    Log.d(TAG, movie.getMovieName() + " |-| " + movie.getMovieId());
                }
            }
        });
    }
}
