package msa.dagandapp.mydaggy;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import msa.dagandapp.DagAndApp;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

@Module
public class DagAndAppModule {

    @Provides
    Context provideContext(DagAndApp application) {
        return application.getApplicationContext();
    }
}
