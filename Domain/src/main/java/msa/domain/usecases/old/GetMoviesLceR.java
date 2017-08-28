package msa.domain.usecases.old;

import java.util.LinkedHashMap;

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

public class GetMoviesLceR extends UseCaseTypeTwo<Lce<LinkedHashMap<String, Movie>>, GetMoviesLceR.Params> {

    private final Repository repository;

    @Inject
    public GetMoviesLceR(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<Lce<LinkedHashMap<String, Movie>>> buildUseCaseObservable(Params params) {
        return repository.getMoviesLceR(params.page);
    }

    public static final class Params {

        private final int page;

        private Params(int page) {
            this.page = page;
        }

        public static GetMoviesLceR.Params setPage(int page) {
            return new GetMoviesLceR.Params(page);
        }
    }
}
