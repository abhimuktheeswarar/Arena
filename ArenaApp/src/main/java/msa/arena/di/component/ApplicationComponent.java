package msa.arena.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import msa.arena.ArenaApplication;
import msa.arena.di.module.ActivityBuildersModule;
import msa.arena.di.module.ApplicationModule;
import msa.arena.di.module.UseCaseModule;
import msa.domain.Repository;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, ApplicationModule.class, ActivityBuildersModule.class, UseCaseModule.class})
public interface ApplicationComponent {

    void inject(ArenaApplication arenaApplication);

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    Repository repository();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}

