package msa.arena.di.module;

import android.app.Application;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
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
    Repository provideRepository(ArenaRepository arenaRepository) {
        return arenaRepository;
    }

    @Provides
    @Singleton
    public Observable<Boolean> observeNetworkConnectivity(Application application) {
        return ReactiveNetwork.observeNetworkConnectivity(application).map(Connectivity::isAvailable).subscribeOn(Schedulers.io());
    }


}


