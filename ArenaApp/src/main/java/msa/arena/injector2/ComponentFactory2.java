package msa.arena.injector2;

import android.app.Application;

import msa.arena.injector2.components2.AppComponent2;
import msa.arena.injector2.components2.DaggerAppComponent2;
import msa.arena.injector2.modules2.AppModule2;

/**
 * Created by Abhimuktheeswarar on 12-06-2017.
 */

public class ComponentFactory2 {


    public AppComponent2 buildComponent(Application context) {
        return componentBuilder(context).build();
    }

    // We override it for functional tests.
    DaggerAppComponent2.Builder componentBuilder(Application context) {
        return DaggerAppComponent2.builder().appModule2(new AppModule2(context));
    }


}
