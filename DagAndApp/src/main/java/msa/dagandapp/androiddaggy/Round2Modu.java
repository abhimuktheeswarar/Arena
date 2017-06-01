package msa.dagandapp.androiddaggy;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import msa.dagandapp.mydaggy.PerActivity;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

@Module//(subcomponents = Round2SubComponent.class)
public class Round2Modu {

    @Provides
    @PerActivity
    @Named("msg")
    public String giveMessage(Context context) {
        return "Hello World!";
    }
}
