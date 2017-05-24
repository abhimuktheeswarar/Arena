package msa.dagandapp.androiddaggy;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import msa.dagandapp.DagAndApp;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, BuildersModule.class})
public interface AppComponent {

    void inject(DagAndApp app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(DagAndApp application);

        AppComponent build();
    }
}