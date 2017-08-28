package msa.rehearsal.round2.subround2_1;

import android.view.View;

import com.airbnb.epoxy.EpoxyController;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.OnModelClickListener;
import com.msa.domain.entities.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.processors.BehaviorProcessor;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

class MovieEpoxyController extends EpoxyController implements OnModelClickListener {

    private final BehaviorProcessor<Movie> movieBehaviorProcessor = BehaviorProcessor.create();
    private final BehaviorProcessor<Integer> integerBehaviorProcessor = BehaviorProcessor.create();
    private List<Movie> movies;

    MovieEpoxyController() {
        movies = new ArrayList<>();
        //setDebugLoggingEnabled(true);
    }

    void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    void addMovie(Movie movie) {
        movies.add(movie);
    }

    @Override
    protected void buildModels() {

        for (Movie movie : movies) {
            {
                //Log.d(MovieEpoxyController.class.getSimpleName(), movie.getMovieName());
                new MovieItemModel_().id(movie.getMovieId()).movieId(movie.getMovieId()).movieName(movie.getMovieName()).isFavorite(movie.isFavorite()).onClickListener(this).addTo(this);
            }
        }
    }

    @Override
    public void onClick(EpoxyModel model, Object parentView, View clickedView, int position) {
        MovieItemModel movieItemModel = (MovieItemModel) model;
        movieBehaviorProcessor.onNext(movies.get(position));



    }

    Observable<Movie> getSelectedMovie() {
        return movieBehaviorProcessor.toObservable();
    }

    Observable<Integer> getSelectedPosition() {
        return integerBehaviorProcessor.toObservable();
    }
}
