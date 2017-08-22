package msa.domain.usecases;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import msa.domain.Repository;
import msa.domain.entities.Movie;
import msa.domain.interactor.old.UseCaseTypeFour;

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
