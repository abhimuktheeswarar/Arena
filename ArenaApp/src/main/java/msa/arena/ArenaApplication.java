package msa.arena;

import android.app.Application;

import msa.arena.injector.components.ApplicationComponent;
import msa.arena.injector.components.DaggerApplicationComponent;
import msa.arena.injector.modules.ApplicationModule;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public class ArenaApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent =
                DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
  }
}
