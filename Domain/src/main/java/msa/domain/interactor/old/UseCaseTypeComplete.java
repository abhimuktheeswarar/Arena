/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package msa.domain.interactor.old;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import msa.domain.Repository;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCaseTypeComplete<Params> {

    protected final Repository repository;
    private final Scheduler mExecutorThread;
    private final Scheduler mUiThread;

    public UseCaseTypeComplete(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        this.repository = repository;
        this.mExecutorThread = mUiThread;
        this.mUiThread = mExecutorThread;
    }

    /**
     * Builds an {@link Completable} which will be used when executing the current {@link UseCaseTypeComplete}.
     */
    protected abstract Completable buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public Completable execute(Params params) {
        return this.buildUseCaseObservable(params)
                .subscribeOn(mExecutorThread)
                .observeOn(mUiThread);

    }
}
