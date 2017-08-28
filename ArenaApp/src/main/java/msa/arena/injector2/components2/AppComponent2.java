package msa.arena.injector2.components2;

import javax.inject.Singleton;

import dagger.Component;
import msa.arena.injector2.AppComponentFactory2;
import msa.arena.injector2.modules2.AppModule2;

/**
 * Created by Abhimuktheeswarar on 12-06-2017.
 */
@Singleton
@Component(modules = {AppModule2.class})
public interface AppComponent2 extends BaseComponent2 {


    void inject(AppComponentFactory2 componentFactory2);
}
