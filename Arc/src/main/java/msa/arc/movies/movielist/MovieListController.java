package msa.arc.movies.movielist;

import com.airbnb.epoxy.Typed2EpoxyController;
import com.github.davidmoten.rx2.util.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;

/**
 * Created by Abhimuktheeswarar on 11-06-2017.
 */

public class MovieListController extends Typed2EpoxyController<List<Movie>, Boolean> implements MovieItemModel.MovieItemClickListener {

    private final BehaviorProcessor<Pair<String, Boolean>> movieBehaviorProcessor = BehaviorProcessor.create();

    private final BehaviorProcessor<Integer> integerBehaviorProcessor = BehaviorProcessor.create();


    void setMovies(Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) {
        setData(new ArrayList<>(linkedHashMapLce.getData().values()), linkedHashMapLce.isLoading());

    }


    @Override
    protected void buildModels(List<Movie> movies, Boolean data2) {

        for (Movie movie : movies) {
            {
                //Log.d(MovieEpoxyController.class.getSimpleName(), movie.getMovieName());
                new MovieItemModel_().id(movie.getMovieId()).movieId(movie.getMovieId()).movieName(movie.getMovieName()).isFavorite(movie.isFavorite()).movieItemClickListener(this).addTo(this);
            }
        }

    }

    @Override
    public void onClickFavorite(String movieId, boolean isFavorite) {
        movieBehaviorProcessor.onNext(Pair.create(movieId, isFavorite));
    }

    Flowable<Pair<String, Boolean>> listenForFavorite() {
        return movieBehaviorProcessor;
    }
}
