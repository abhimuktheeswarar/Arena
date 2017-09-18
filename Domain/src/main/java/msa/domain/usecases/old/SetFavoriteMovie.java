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

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.interactor.old.UseCaseTypeComplete;

/**
 * Created by Abhimuktheeswarar on 13-06-2017.
 */

public class SetFavoriteMovie extends UseCaseTypeComplete<SetFavoriteMovie.Params> {


    @Inject
    public SetFavoriteMovie(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(repository, mUiThread, mExecutorThread);
    }

    @Override
    protected Completable buildUseCaseObservable(Params params) {
        return repository.setFavoriteMovie(params.movieId, params.isFavorite);
    }

    public static final class Params {

        private final String movieId;
        private final boolean isFavorite;

        private Params(String movieId, boolean isFavorite) {
            this.movieId = movieId;
            this.isFavorite = isFavorite;
        }

        public static SetFavoriteMovie.Params setFavorite(String movieId, boolean isFavorite) {
            return new SetFavoriteMovie.Params(movieId, isFavorite);
        }
    }
}
