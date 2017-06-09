package msa.arc.di.module;

import com.msa.domain.Repository;
import com.msa.domain.usecases.GetMovieHashes;
import com.msa.domain.usecases.GetMoviesTypeOne;
import com.msa.domain.usecases.GetMoviesTypeThree;
import com.msa.domain.usecases.GetMoviesTypeTwo;
import com.msa.domain.usecases.GetUserTypeOne;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    GetUserTypeOne provideGetUserTypeOneUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetUserTypeOne(repository, mUiThread, mExecutorThread);
    }

    @Provides
    GetMovieHashes provideGetMovieHashesUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMovieHashes(repository, mUiThread, mExecutorThread);
    }

    @Provides
    GetMoviesTypeOne provideGetMoviesTypeOneUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeOne(repository, mUiThread, mExecutorThread);
    }

    @Provides
    GetMoviesTypeTwo provideGetMoviesTypeTwoUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeTwo(repository, mUiThread, mExecutorThread);
    }

    @Provides
    GetMoviesTypeThree provideGetMoviesTypeThreeUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeThree(repository, mUiThread, mExecutorThread);
    }

    @Provides
    String provideName() {
        return "Abhi Muktheeswarar";
    }
}
