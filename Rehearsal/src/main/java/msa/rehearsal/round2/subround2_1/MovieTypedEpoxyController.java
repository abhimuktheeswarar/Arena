package msa.rehearsal.round2.subround2_1;

import android.util.Log;
import android.view.View;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.OnModelClickListener;
import com.airbnb.epoxy.TypedEpoxyController;
import com.msa.domain.entities.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.processors.BehaviorProcessor;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

class MovieTypedEpoxyController extends TypedEpoxyController<List<Movie>> implements OnModelClickListener, MovieItemTypeModel.MovieItemTypeClickListener {

    private final BehaviorProcessor<Movie> movieBehaviorProcessor = BehaviorProcessor.create();
    private final BehaviorProcessor<Integer> integerBehaviorProcessor = BehaviorProcessor.create();

    MovieTypedEpoxyController() {

    }

    @Override
    protected void buildModels(List<Movie> data) {

        for (Movie movie : data) {
            {
                //Log.d(MovieEpoxyController.class.getSimpleName(), movie.getMovieName());
                //new MovieItemModel_().id(movie.getMovieId()).movieId(movie.getMovieId()).movieName(movie.getMovieName()).isFavorite(movie.isFavorite()).onClickListener(this).addTo(this);
                new MovieItemTypeModel_().id(movie.getMovieId()).movie(movie).movieItemTypeClickListener(this).addTo(this);
            }
        }
    }


    @Override
    public void onClick(EpoxyModel model, Object parentView, View clickedView, int position) {
        MovieItemModel movieItemModel = (MovieItemModel) model;
        //movieBehaviorProcessor.onNext(getCurrentData().get(position));
        Log.d(MovieTypedEpoxyController.class.getSimpleName(), "id = " + clickedView.getId());
        integerBehaviorProcessor.onNext(position);

    }

    Observable<Movie> getSelectedMovie() {
        return movieBehaviorProcessor.toObservable();
    }

    @Override
    public void onClickFavorite(Movie movie) {
        movieBehaviorProcessor.onNext(movie);

    }
}
