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
    public Observable<ResourceCarrier<LinkedHashMap<String, Movie>>> buildUseCaseObservable(Params params) {
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
