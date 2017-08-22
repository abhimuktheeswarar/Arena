package msa.domain.usecases;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.entities.User;
import msa.domain.interactor.old.UseCaseTypeThree;

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
