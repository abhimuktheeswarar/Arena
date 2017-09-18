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
