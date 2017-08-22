package msa.arena.injector2.modules2;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import msa.data.repository.ArenaRepository;
import msa.domain.Repository;

/**
 * Created by Abhimuktheeswarar on 12-06-2017.
 */
@Module
public class AppModule2 {


    private final Context applicationContext;

    public AppModule2(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

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
    Repository provideRepository(ArenaRepository medikoeRepository) {
        return medikoeRepository;
    }
}
