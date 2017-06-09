package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.User;
import com.msa.domain.interactor.UseCaseTypeThree;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetUserTypeOne extends UseCaseTypeThree<User, Void> {

    private final Repository repository;

    @Inject
    public GetUserTypeOne(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }


    @Override
    protected Observable<User> buildUseCaseObservable(Void aVoid) {
        return repository.getUser();
    }
}
