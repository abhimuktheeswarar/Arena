package msa.dagandapp.androiddaggy;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import msa.dagandapp.round2.Round2Activity;
import msa.dagandapp.simple.FeatureActivity;

/**
 * Created by Abhimuktheeswarar on 25-05-2017.
 */

@Subcomponent
public interface Round2SubComponent extends AndroidInjector<Round2Activity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<Round2Activity> {
    }
}
