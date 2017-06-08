package msa.arc.di.component;

import android.app.Application;

import com.msa.domain.Repository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import io.reactivex.Scheduler;
import msa.arc.ArcApp;
import msa.arc.di.module.ArcAppModule;
import msa.arc.di.module.BuildersModule;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, ArcAppModule.class, BuildersModule.class})
public interface ArcAppComponent {

    void inject(ArcApp arcApp);

    @Named("ui_thread")
    Scheduler uiThread();

    @Named("executor_thread")
    Scheduler executorThread();

    Repository repository();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ArcAppComponent build();
    }
}

