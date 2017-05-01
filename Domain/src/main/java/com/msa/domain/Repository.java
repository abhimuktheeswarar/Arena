package com.msa.domain;

import com.msa.domain.entities.Movie;

import io.reactivex.Observable;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public interface Repository {


    Observable<Movie> getMovies(int page);
}
