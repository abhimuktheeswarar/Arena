package msa.dagandapp.androiddaggy;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import msa.dagandapp.mydaggy.PerActivity;
import msa.dagandapp.round2.Round2Activity;

/**
 * Created by Abhimuktheeswarar on 25-05-2017.
 */
@PerActivity
@Subcomponent(modules = Round2Modu.class)
public interface Round2SubComponent extends AndroidInjector<Round2Activity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<Round2Activity> {
    }
}
