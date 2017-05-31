package msa.dagandapp.androiddaggy;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import msa.dagandapp.round2.Round2Activity;
import msa.dagandapp.simple.FeatureActivity;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

@Module
public abstract class BuildersModule {

    @Binds
    @IntoMap
    @ActivityKey(FeatureActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindFeatureActivityInjectorFactory(FeatureSubComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(Round2Activity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindRound2ActivityInjectorFactory(Round2SubComponent.Builder builder);

}