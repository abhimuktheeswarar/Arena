package msa.rehearsal.injector.components;

import android.app.Activity;

import dagger.Component;
import msa.rehearsal.injector.PerActivity;
import msa.rehearsal.injector.modules.ActivityModule;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //Exposed to sub-graphs.
    Activity getActivity();
}
