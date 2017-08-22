package msa.domain.usecases.old;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.entities.Movie;
import msa.domain.interactor.old.UseCaseTypeThree;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMoviesTypeThree extends UseCaseTypeThree<Movie, Integer> {

    private final Repository repository;

    @Inject
    public GetMoviesTypeThree(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable<Movie> buildUseCaseObservable(Integer page) {
        return repository.getMovies(page);
    }
}
