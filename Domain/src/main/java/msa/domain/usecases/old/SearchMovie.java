package msa.domain.usecases.old;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.entities.Movie;
import msa.domain.interactor.old.UseCase;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class SearchMovie extends UseCase<List<Movie>, String> {

    private final Repository repository;

    @Inject
    public SearchMovie(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable<List<Movie>> buildUseCaseObservable(String query) {
        return repository.searchMovie(query);
    }
}
