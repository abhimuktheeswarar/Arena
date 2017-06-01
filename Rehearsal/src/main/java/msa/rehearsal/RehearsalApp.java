package msa.rehearsal;

import android.app.Application;

import msa.rehearsal.injector.components.ApplicationComponent;
import msa.rehearsal.injector.components.DaggerApplicationComponent;
import msa.rehearsal.injector.modules.ApplicationModule;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

public class RehearsalApp extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
