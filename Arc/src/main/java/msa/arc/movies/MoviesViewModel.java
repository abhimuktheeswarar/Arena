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

package msa.arc.movies;

import javax.inject.Inject;

import io.reactivex.Completable;
import msa.arc.base.BaseViewModel;
import msa.domain.entities.User;
import msa.domain.usecases.old.GetMovieHashes;
import msa.domain.usecases.old.UpdateUserTypeOne;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
public class MoviesViewModel extends BaseViewModel {

    private final GetMovieHashes getMovieHashes;
    private final UpdateUserTypeOne updateUserTypeOne;
    public int a = 2;


    @Inject
    public MoviesViewModel(GetMovieHashes getMovieHashes, UpdateUserTypeOne updateUserTypeOne) {
        this.getMovieHashes = getMovieHashes;
        this.updateUserTypeOne = updateUserTypeOne;
    }

    public int getA() {
        return a;
    }

    public void add() {
        a++;
    }

    Completable updateUser(String displayName) {
        return updateUserTypeOne.execute(UpdateUserTypeOne.Params.setUser(new User("id_" + displayName, displayName)));
    }


}
