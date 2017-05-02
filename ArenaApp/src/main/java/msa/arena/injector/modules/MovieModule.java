package msa.arena.injector.modules;

import com.msa.domain.Repository;
import com.msa.domain.usecases.GetMoviesTypeOne;
import com.msa.domain.usecases.GetMoviesTypeTwo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import msa.arena.injector.PerActivity;

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

}
