package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.Movie;
import com.msa.domain.interactor.UseCaseTypeTwo;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMoviesTypeTwo extends UseCaseTypeTwo<Movie, Integer> {

    private final Repository repository;

    @Inject
    public GetMoviesTypeTwo(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<Movie> buildUseCaseObservable(Integer page) {
        return repository.getMoviesTypeTwo(page);
    }
}
