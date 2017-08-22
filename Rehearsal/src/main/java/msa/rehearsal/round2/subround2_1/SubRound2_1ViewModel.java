package msa.rehearsal.round2.subround2_1;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import msa.domain.entities.Movie;
import msa.domain.usecases.GetMovieHashes;
import msa.domain.usecases.GetMoviesTypeFive;
import msa.domain.usecases.GetMoviesTypeFour;
import msa.domain.usecases.GetMoviesTypeSix;
import msa.rehearsal.injector.PerActivity;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

@PerActivity
public class SubRound2_1ViewModel {

    private final GetMoviesTypeFour getMoviesTypeFour;
    private final GetMoviesTypeFive getMoviesTypeFive;
    private final GetMoviesTypeSix getMoviesTypeSix;
    private final GetMovieHashes getMovieHashes;

    @Inject
    public SubRound2_1ViewModel(GetMoviesTypeFour getMoviesTypeFour, GetMoviesTypeFive getMoviesTypeFive, GetMoviesTypeSix getMoviesTypeSix, GetMovieHashes getMovieHashes) {
        this.getMoviesTypeFour = getMoviesTypeFour;
        this.getMoviesTypeFive = getMoviesTypeFive;
        this.getMoviesTypeSix = getMoviesTypeSix;
        this.getMovieHashes = getMovieHashes;
    }

    Observable<List<Movie>> getMovies(int page) {
        return getMoviesTypeFour.execute(page);
    }

    Observable<LinkedHashMap<String, Movie>> getMovieHashes(int page) {
        return getMovieHashes.execute(page);
    }

   /* Observable<List<Movie>> getMovieHashList(int page) {
        return getMovieHashes(page).map(new Function<LinkedHashMap<String, Movie>, List<Movie>>() {
            @Override
            public List<Movie> apply(@NonNull LinkedHashMap<String, Movie> stringMovieHashMap) throws Exception {
                return new ArrayList<Movie>(stringMovieHashMap.values());
            }
        });
    }*/

    Flowable<List<Movie>> getMovieFlow(int page) {
        //return getMoviesTypeSix.execute(page);
        Log.d(SubRound2_1ViewModel.class.getSimpleName(), "getMovieFlow = " + page);
        List<Movie> movieList = new LinkedList<>();
        for (int i = page; i < page + 20; i++)
            movieList.add(new Movie(UUID.randomUUID().toString() + i, "Movie " + i, i % 2 == 0));
        return Flowable.just(movieList);
    }

    Observable<Movie> getMovieFeed(int page) {
        return getMoviesTypeFive.execute(page);
    }
}
