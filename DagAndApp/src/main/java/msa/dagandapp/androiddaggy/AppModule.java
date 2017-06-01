package msa.dagandapp.androiddaggy;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import msa.dagandapp.DagAndApp;
import msa.dagandapp.simple.SomeClientApi;
import msa.dagandapp.simple.SomeClientApiImpl;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

@Module(subcomponents = {FeatureSubComponent.class, Round2SubComponent.class})
class AppModule {

    @Provides
    Context provideContext(DagAndApp application) {
        return application.getApplicationContext();
    }


    /*@Provides SomeClientApi provideSomeClientApi() {
        return new SomeClientApiImpl(66);
    }*/
}