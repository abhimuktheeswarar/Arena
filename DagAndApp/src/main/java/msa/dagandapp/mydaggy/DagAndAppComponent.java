package msa.dagandapp.mydaggy;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import msa.dagandapp.DagAndApp;
import msa.dagandapp.androiddaggy.AppComponent;
import msa.dagandapp.androiddaggy.BuildersModule;

/**
 * Created by Abhimuktheeswarar on 25-05-2017.
 */

@Component(modules = {AndroidSupportInjectionModule.class, DagAndAppModule.class, Round2Module.class})
public interface DagAndAppComponent {

    void inject(DagAndApp dagAndApp);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DagAndAppComponent.Builder application(DagAndApp dagAndApp);

        DagAndAppComponent build();
    }
}
