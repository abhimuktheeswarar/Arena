package msa.arc.di.module;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public class MovieModule {

    /*@Provides
    GetMovieHashes provideGetMovieHashesUseCase(Repository repository, @Named("executor_thread") Scheduler mUiThread, @Named("ui_thread") Scheduler mExecutorThread) {
        return new GetMovieHashes(repository, mUiThread, mExecutorThread);
    }*/

    @Provides
    String provideName() {
        return "Abhi Muktheeswarar";
    }
}
