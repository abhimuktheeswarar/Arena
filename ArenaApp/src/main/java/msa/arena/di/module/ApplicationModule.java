package msa.arena.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import msa.arena.utilities.UIThread;
import msa.data.executor.JobExecutor;
import msa.data.repository.ArenaRepository;
import msa.data.repository.DataStoreFactory;
import msa.domain.Repository;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;

/**
 * Created by Abhimuktheeswarar on 25-06-2017.
 */

@Module(includes = {ViewModelModule.class})
public class ApplicationModule {

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    DataStoreFactory provideDataStoreFactory(Application application) {
        return new DataStoreFactory(application);
    }

    @Provides
    @Singleton
    Repository provideRepository(ArenaRepository offoRepository) {
        return offoRepository;
    }

}


