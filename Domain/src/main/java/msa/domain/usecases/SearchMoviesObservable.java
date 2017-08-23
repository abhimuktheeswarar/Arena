package msa.domain.usecases;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import msa.domain.Repository;
import msa.domain.entities.Movie;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.interactor.UseCaseObservable;

/**
 * Created by Abhimuktheeswarar on 22-08-2017.
 */

public class SearchMoviesObservable extends UseCaseObservable<ResourceCarrier<LinkedHashMap<String, Movie>>, SearchMoviesObservable.Params> {

    @Inject
    public SearchMoviesObservable(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repository, threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<ResourceCarrier<LinkedHashMap<String, Movie>>> buildUseCaseObservable(Params params) {
        if (params.query.length() == 0)
            return Observable.just(ResourceCarrier.completed(new LinkedHashMap<String, Movie>()));
        else return repository.searchMoviesObservable(params.query);
    }

    public static final class Params {

        private final String query;

        private Params(String query) {
            this.query = query;
        }

        public static SearchMoviesObservable.Params setQuery(String query) {
            return new SearchMoviesObservable.Params(query);
        }
    }
}
