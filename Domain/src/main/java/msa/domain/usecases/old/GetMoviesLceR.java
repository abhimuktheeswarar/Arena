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
