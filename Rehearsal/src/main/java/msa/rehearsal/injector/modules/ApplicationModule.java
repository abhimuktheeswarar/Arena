package msa.rehearsal.injector.modules;

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
import msa.rehearsal.RehearsalApp;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@Module
public class ApplicationModule {

    private final RehearsalApp rehearsalApp;

    public ApplicationModule(RehearsalApp rehearsalApp) {
        this.rehearsalApp = rehearsalApp;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return rehearsalApp;
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
    Repository provideMedikoeRepository(ArenaRepository medikoeRepository) {
        return medikoeRepository;
    }
}
