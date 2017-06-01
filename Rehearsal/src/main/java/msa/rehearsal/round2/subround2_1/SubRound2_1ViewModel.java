package msa.rehearsal.round2.subround2_1;

import com.msa.domain.entities.Movie;
import com.msa.domain.usecases.GetMoviesTypeFive;
import com.msa.domain.usecases.GetMoviesTypeFour;
import com.msa.domain.usecases.GetMoviesTypeOne;
import com.msa.domain.usecases.GetMoviesTypeThree;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import msa.rehearsal.injector.PerActivity;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

@PerActivity
public class SubRound2_1ViewModel {

    private final GetMoviesTypeFour getMoviesTypeFour;
    private final GetMoviesTypeFive getMoviesTypeFive;

    @Inject
    public SubRound2_1ViewModel(GetMoviesTypeFour getMoviesTypeFour, GetMoviesTypeFive getMoviesTypeFive) {
        this.getMoviesTypeFour = getMoviesTypeFour;
        this.getMoviesTypeFive = getMoviesTypeFive;
    }

    Observable<List<Movie>> getMovies(int page) {
        return getMoviesTypeFour.execute(page);
    }

    Observable<Movie> getMovieFeed(int page) {
        return getMoviesTypeFive.execute(page);
    }
}
