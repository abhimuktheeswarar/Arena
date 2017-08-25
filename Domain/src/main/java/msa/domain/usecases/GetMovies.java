package msa.domain.usecases;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import msa.domain.Repository;
import msa.domain.entities.Movie;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.interactor.UseCaseFlowable;

/**
 * Created by Abhimuktheeswarar on 25-08-2017.
 */

public class GetMovies extends UseCaseFlowable<ResourceCarrier<LinkedHashMap<String, Movie>>, GetMovies.Params> {


    @Inject
    public GetMovies(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repository, threadExecutor, postExecutionThread);
    }

    @Override
    protected Flowable<ResourceCarrier<LinkedHashMap<String, Movie>>> buildUseCaseFlowable(Params params) {
        return repository.getMovies(params.getPage());
    }

    public static final class Params {

        private final int page;

        private Params(Builder builder) {
            page = builder.page;
        }

        public static Builder newBuilder() {
            return new Builder();
        }


        public int getPage() {
            return page;
        }

        public static final class Builder {
            private int page;

            private Builder() {
            }

            public Builder page(int page) {
                this.page = page;
                return this;
            }

            public Params build() {
                return new Params(this);
            }
        }
    }
}
