package msa.arc.movies;

import javax.inject.Inject;

import msa.arc.base.BaseViewModel;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

public class MovieChildViewModel extends BaseViewModel {


    private final String name;
    private int i;

    @Inject
    public MovieChildViewModel(String name) {
        this.name = name;
        i = 10;
    }

    public String getName() {
        return name;
    }

    public void setI() {
        i++;
    }

    public int getI() {
        return i;
    }
}
