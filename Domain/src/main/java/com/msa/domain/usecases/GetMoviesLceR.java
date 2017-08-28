package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.interactor.UseCaseTypeTwo;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMoviesLceR extends UseCaseTypeTwo<Lce<LinkedHashMap<String, Movie>>, GetMoviesLceR.Params> {

    private final Repository repository;

    @Inject
    public GetMoviesLceR(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<Lce<LinkedHashMap<String, Movie>>> buildUseCaseObservable(Params params) {
        return repository.getMoviesLceR(params.page);
    }

    public static final class Params {

        private final int page;

        private Params(int page) {
            this.page = page;
        }

        public static GetMoviesLceR.Params setPage(int page) {
            return new GetMoviesLceR.Params(page);
        }
    }
}
