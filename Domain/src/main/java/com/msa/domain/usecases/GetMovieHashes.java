package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.Movie;
import com.msa.domain.interactor.UseCaseTypeThree;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMovieHashes extends UseCaseTypeThree<LinkedHashMap<String, Movie>, Integer> {

    private final Repository repository;

    @Inject
    public GetMovieHashes(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable<LinkedHashMap<String, Movie>> buildUseCaseObservable(Integer page) {
        return repository.getMovieHashes(page);
    }
}
