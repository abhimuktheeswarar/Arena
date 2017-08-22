package msa.arena.di.module;

import dagger.Module;
import dagger.Provides;
import msa.domain.Repository;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;
import msa.domain.usecases.SearchMovies;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Module
public class UseCaseModule {

    @Provides
    SearchMovies provideSearchMoviesUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new SearchMovies(repository, threadExecutor, postExecutionThread);
    }

}
