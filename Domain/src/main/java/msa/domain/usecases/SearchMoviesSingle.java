package msa.domain.usecases;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Single;
import msa.domain.Repository;
import msa.domain.entities.Movie;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.interactor.UseCaseSingle;

/**
 * Created by Abhimuktheeswarar on 22-08-2017.
 */

public class SearchMoviesSingle extends UseCaseSingle<ResourceCarrier<LinkedHashMap<String, Movie>>, SearchMoviesSingle.Params> {

    @Inject
    public SearchMoviesSingle(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repository, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<ResourceCarrier<LinkedHashMap<String, Movie>>> buildUseCaseSingle(Params params) {
        if (params.query.length() == 0)
            return Single.just(ResourceCarrier.completed(new LinkedHashMap<String, Movie>()));
        else return repository.searchMoviesSingle(params.query);
    }

    public static final class Params {

        private final String query;

        private Params(String query) {
            this.query = query;
        }

        public static SearchMoviesSingle.Params setQuery(String query) {
            return new SearchMoviesSingle.Params(query);
        }
    }
}
