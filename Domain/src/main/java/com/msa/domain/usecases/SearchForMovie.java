package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.Movie;
import com.msa.domain.interactor.UseCaseTypeFour;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Created by Abhimuktheeswarar on 04-05-2017
 */

public class SearchForMovie extends UseCaseTypeFour<List<Movie>, String> {

    public SearchForMovie(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(repository, mUiThread, mExecutorThread);
    }

    @Override
    protected Single<List<Movie>> buildUseCaseSingle(String query) {
        return repository.searchForMovie(query);
    }
}
