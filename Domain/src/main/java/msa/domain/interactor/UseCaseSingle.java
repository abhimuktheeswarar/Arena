package msa.domain.interactor;

/**
 * Created by Abhimuktheeswarar on 25-06-2017
 */

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import msa.domain.Repository;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a {@link DisposableSingleObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCaseSingle<T, Params> {

    protected final Repository repository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    public UseCaseSingle(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.repository = repository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;

    }

    /**
     * Builds an {@link Single} which will be used when executing the current {@link UseCaseSingle}.
     */
    protected abstract Single<T> buildUseCaseSingle(Params params);

    /**
     * Executes the current use case.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public Single<T> execute(Params params) {
        return this.buildUseCaseSingle(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }
}
