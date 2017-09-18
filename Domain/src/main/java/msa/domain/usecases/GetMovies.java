/*
 * Copyright 2017, Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
