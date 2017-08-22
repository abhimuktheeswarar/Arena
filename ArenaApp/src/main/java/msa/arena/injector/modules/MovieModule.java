package msa.arena.injector.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import msa.arena.injector.PerActivity;
import msa.domain.Repository;
import msa.domain.usecases.old.GetMoviesTypeOne;
import msa.domain.usecases.old.GetMoviesTypeThree;
import msa.domain.usecases.old.GetMoviesTypeTwo;
import msa.domain.usecases.old.GetMoviesTypeTwoLce;
import msa.domain.usecases.old.SearchForMovie;
import msa.domain.usecases.old.SearchMovie;
import msa.domain.usecases.old.SearchMovieTypeTwo;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@Module
public class MovieModule {

    @Provides
    @PerActivity
    GetMoviesTypeOne provideGetMoviesTypeOneUseCase(
            Repository repository,
            @Named("executor_thread") Scheduler mUiThread,
            @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeOne(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMoviesTypeTwo provideGetMoviesTypeTwoUseCase(
            Repository repository,
            @Named("executor_thread") Scheduler mUiThread,
            @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeTwo(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMoviesTypeTwoLce provideGetMoviesTypeTwoLceUseCase(
            Repository repository,
            @Named("executor_thread") Scheduler mUiThread,
            @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeTwoLce(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMoviesTypeThree provideGetMoviesTypeThreeUseCase(
            Repository repository,
            @Named("executor_thread") Scheduler mUiThread,
            @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeThree(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    SearchMovie provideSearchMovieUseCase(
            Repository repository,
            @Named("executor_thread") Scheduler mUiThread,
            @Named("ui_thread") Scheduler mExecutorThread) {
        return new SearchMovie(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    SearchMovieTypeTwo provideSearchMovieTypeTwoUseCase(
            Repository repository,
            @Named("executor_thread") Scheduler mUiThread,
            @Named("ui_thread") Scheduler mExecutorThread) {
        return new SearchMovieTypeTwo(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    SearchForMovie provideSearchForMovieUseCase(
            Repository repository,
            @Named("executor_thread") Scheduler mUiThread,
            @Named("ui_thread") Scheduler mExecutorThread) {
        return new SearchForMovie(repository, mUiThread, mExecutorThread);
  }
}
