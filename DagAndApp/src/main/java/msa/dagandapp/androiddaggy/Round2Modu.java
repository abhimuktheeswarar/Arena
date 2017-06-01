package msa.dagandapp.androiddaggy;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import msa.dagandapp.mydaggy.PerActivity;
import msa.dagandapp.round2.Round2Activity;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

@Module//(subcomponents = Round2SubComponent.class)
public abstract class Round2Modu {

    @Provides
    @PerActivity
    @Named("msg")
    public static String giveMessage(Context context) {
        return "Hello Universe!";
    }

    @Binds
    @IntoMap
    @ActivityKey(Round2Activity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindRound2ActivityInjectorFactory(Round2SubComponent.Builder builder);
}
