package msa.arena.injector2;

import msa.arena.injector2.components2.AppComponent2;

/**
 * Created by Abhimuktheeswarar on 12-06-2017.
 */

public class AppComponentFactory2 {

    AppComponent2 appComponent2;


    private void buildComponentAndInject() {
        //appComponent2 = componentFactory().buildComponent(this);
        appComponent2.inject(this);
    }


    public ComponentFactory2 componentFactory() {
        return new ComponentFactory2();
    }
}
