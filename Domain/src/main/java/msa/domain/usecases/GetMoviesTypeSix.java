package msa.domain.usecases;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.entities.Movie;
import msa.domain.interactor.old.UseCaseTypeTwo;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMoviesTypeSix extends UseCaseTypeTwo<List<Movie>, Integer> {

    private final Repository repository;

    @Inject
    public GetMoviesTypeSix(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<List<Movie>> buildUseCaseObservable(Integer page) {
        return repository.getMovieFlow(page);
    }
}
