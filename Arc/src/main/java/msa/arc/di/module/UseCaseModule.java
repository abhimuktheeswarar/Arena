package msa.arc.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.usecases.GetMovieHashes;
import msa.domain.usecases.GetMoviesLce;
import msa.domain.usecases.GetMoviesLceR;
import msa.domain.usecases.GetMoviesTypeOne;
import msa.domain.usecases.GetMoviesTypeThree;
import msa.domain.usecases.GetMoviesTypeTwo;
import msa.domain.usecases.GetUserTypeOne;
import msa.domain.usecases.SetFavoriteMovie;
import msa.domain.usecases.UpdateUserTypeOne;

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
    UpdateUserTypeOne provideUpdateUserTypeOneUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new UpdateUserTypeOne(repository, mUiThread, mExecutorThread);
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
    GetMoviesLce provideGetMoviesLceUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesLce(repository, mUiThread, mExecutorThread);
    }

    @Provides
    GetMoviesLceR provideGetMoviesLceRUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesLceR(repository, mUiThread, mExecutorThread);
    }

    @Provides
    SetFavoriteMovie provideSetFavoriteMovieUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new SetFavoriteMovie(repository, mUiThread, mExecutorThread);
    }

    @Provides
    String provideName() {
        return "Abhi Muktheeswarar";
    }
}
