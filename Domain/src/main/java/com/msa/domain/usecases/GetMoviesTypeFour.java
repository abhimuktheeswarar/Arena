package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.Movie;
import com.msa.domain.interactor.UseCaseTypeThree;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMoviesTypeFour extends UseCaseTypeThree<List<Movie>, Integer> {

    private final Repository repository;

    @Inject
    public GetMoviesTypeFour(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable<List<Movie>> buildUseCaseObservable(Integer page) {
        return repository.getMovieList(page);
    }
}
