package msa.domain.usecases;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.entities.Movie;
import msa.domain.interactor.old.UseCase;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMoviesTypeOne extends UseCase<Movie, Integer> {

    private final Repository repository;

    @Inject
    public GetMoviesTypeOne(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable<Movie> buildUseCaseObservable(Integer page) {
        return repository.getMovies(page);
    }
}
