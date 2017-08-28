package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.User;
import com.msa.domain.interactor.UseCaseTypeComplete;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class UpdateUserTypeOne extends UseCaseTypeComplete<UpdateUserTypeOne.Params> {


    @Inject
    public UpdateUserTypeOne(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(repository, mUiThread, mExecutorThread);
    }

    @Override
    protected Completable buildUseCaseObservable(Params params) {
        return repository.updateUser(params.user);
    }


    public static final class Params {

        private final User user;

        private Params(User user) {
            this.user = user;
        }

        public static Params setUser(User user) {
            return new Params(user);
        }
    }
}
