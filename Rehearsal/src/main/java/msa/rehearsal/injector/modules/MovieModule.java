package msa.rehearsal.injector.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.usecases.GetMovieHashes;
import msa.domain.usecases.GetMoviesTypeFive;
import msa.domain.usecases.GetMoviesTypeFour;
import msa.domain.usecases.GetMoviesTypeOne;
import msa.domain.usecases.GetMoviesTypeSix;
import msa.domain.usecases.GetMoviesTypeThree;
import msa.domain.usecases.GetMoviesTypeTwo;
import msa.domain.usecases.SearchForMovie;
import msa.domain.usecases.SearchMovie;
import msa.domain.usecases.SearchMovieTypeTwo;
import msa.rehearsal.injector.PerActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@Module
public class MovieModule {

    @Provides
    @PerActivity
    GetMoviesTypeOne provideGetMoviesTypeOneUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeOne(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMoviesTypeTwo provideGetMoviesTypeTwoUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeTwo(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMoviesTypeThree provideGetMoviesTypeThreeUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeThree(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMoviesTypeFour provideGetMoviesTypeFourUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeFour(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMoviesTypeFive provideGetMoviesTypeFiveUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeFive(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMoviesTypeSix provideGetMoviesTypeSixUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMoviesTypeSix(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    GetMovieHashes provideGetMovieHashesUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMovieHashes(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    SearchMovie provideSearchMovieUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new SearchMovie(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    SearchMovieTypeTwo provideSearchMovieTypeTwoUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new SearchMovieTypeTwo(repository, mUiThread, mExecutorThread);
    }

    @Provides
    @PerActivity
    SearchForMovie provideSearchForMovieUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new SearchForMovie(repository, mUiThread, mExecutorThread);
    }
}
