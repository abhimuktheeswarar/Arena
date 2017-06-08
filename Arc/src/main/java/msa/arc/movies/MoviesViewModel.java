package msa.arc.movies;

import com.msa.domain.usecases.GetMovieHashes;

import javax.inject.Inject;

import msa.arc.base.BaseViewModel;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
public class MoviesViewModel extends BaseViewModel {

    private final GetMovieHashes getMovieHashes;
    public int a = 2;


    @Inject
    public MoviesViewModel(GetMovieHashes getMovieHashes) {
        this.getMovieHashes = getMovieHashes;
    }

    public int getA() {
        return a;
    }

    public void add() {
        a++;
    }
}
