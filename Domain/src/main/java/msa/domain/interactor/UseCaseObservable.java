package msa.domain.interactor;

/**
 * Created by Abhimuktheeswarar on 25-06-2017
 */

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import msa.domain.Repository;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCaseObservable<T, Params> {

    protected final Repository repository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    public UseCaseObservable(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.repository = repository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;

    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCaseObservable}.
     */
    protected abstract Observable<T> buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public Observable<T> execute(Params params) {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }
}
