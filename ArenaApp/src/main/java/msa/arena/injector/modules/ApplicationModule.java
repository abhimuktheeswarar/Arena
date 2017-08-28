package msa.arena.injector.modules;

import android.content.Context;

import com.msa.domain.Repository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import msa.arena.ArenaApplication;
import msa.arena.data.repository.ArenaRepository;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@Module
public class ApplicationModule {

    private final ArenaApplication arenaApplication;

    public ApplicationModule(ArenaApplication arenaApplication) {
        this.arenaApplication = arenaApplication;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return arenaApplication;
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

  /* @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
      return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
      return uiThread;
  }*/

    @Provides
    @Singleton
    Repository provideRepository(ArenaRepository medikoeRepository) {
        return medikoeRepository;
    }
}
