package msa.arc.di.module;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import msa.data.repository.ArenaRepository;
import msa.data.repository.DataStoreFactory;
import msa.domain.Repository;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Module(includes = {ViewModelModule.class})
public class ArcAppModule {

    @Provides
    @Named("executor_thread")
    @Singleton
    Scheduler provideExecutorThread() {
        return Schedulers.io();
    }

    @Provides
    @Named("ui_thread")
    @Singleton
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    DataStoreFactory provideDataStoreFactory(Application application) {
        return new DataStoreFactory(application);
    }

    @Provides
    @Singleton
    Repository provideRepository(ArenaRepository arenaRepository) {
        return arenaRepository;
    }
}
