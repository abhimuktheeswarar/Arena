package msa.arc.di.module;

import com.msa.domain.Repository;
import com.msa.domain.usecases.GetMovieHashes;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public abstract class MovieModule {

    @Provides
    GetMovieHashes provideGetMovieHashesUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMovieHashes(repository, mUiThread, mExecutorThread);
    }
}
