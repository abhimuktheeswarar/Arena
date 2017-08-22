package msa.domain.usecases;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;
import msa.domain.interactor.old.UseCaseTypeTwo;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMoviesTypeTwoLce extends UseCaseTypeTwo<Lce<Movie>, Integer> {

    private final Repository repository;

    @Inject
    public GetMoviesTypeTwoLce(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<Lce<Movie>> buildUseCaseObservable(Integer page) {
        return repository.getMoviesTypeTwoLce(page);
    }
}
