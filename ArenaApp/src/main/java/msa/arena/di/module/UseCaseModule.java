package msa.arena.di.module;

import dagger.Module;
import dagger.Provides;
import msa.domain.Repository;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;
import msa.domain.usecases.GetMovies;
import msa.domain.usecases.SearchMoviesObservable;
import msa.domain.usecases.SearchMoviesSingle;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Module
public class UseCaseModule {

    @Provides
    SearchMoviesSingle provideSearchMoviesSingleUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new SearchMoviesSingle(repository, threadExecutor, postExecutionThread);
    }

    @Provides
    SearchMoviesObservable provideSearchMoviesObservableUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new SearchMoviesObservable(repository, threadExecutor, postExecutionThread);
    }

    @Provides
    GetMovies provideGetMoviesUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetMovies(repository, threadExecutor, postExecutionThread);
    }
}
