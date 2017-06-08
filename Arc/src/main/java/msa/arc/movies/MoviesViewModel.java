package msa.arc.movies;

import javax.inject.Inject;

import msa.arc.base.BaseViewModel;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

public class MoviesViewModel extends BaseViewModel {

    public int a = 2;


    @Inject
    public MoviesViewModel() {
    }

    public int getA() {
        return a;
    }

    public void add() {
        a++;
    }
}
