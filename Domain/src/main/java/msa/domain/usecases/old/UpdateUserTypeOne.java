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
import msa.domain.entities.User;
import msa.domain.interactor.old.UseCaseTypeComplete;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class UpdateUserTypeOne extends UseCaseTypeComplete<UpdateUserTypeOne.Params> {


    @Inject
    public UpdateUserTypeOne(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(repository, mUiThread, mExecutorThread);
    }

    @Override
    protected Completable buildUseCaseObservable(Params params) {
        return repository.updateUser(params.user);
    }


    public static final class Params {

        private final User user;

        private Params(User user) {
            this.user = user;
        }

        public static Params setUser(User user) {
            return new Params(user);
        }
    }
}
