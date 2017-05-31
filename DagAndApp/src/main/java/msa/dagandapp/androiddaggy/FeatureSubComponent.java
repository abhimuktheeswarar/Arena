package msa.dagandapp.androiddaggy;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import msa.dagandapp.simple.FeatureActivity;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

@Subcomponent
interface FeatureSubComponent extends AndroidInjector<FeatureActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FeatureActivity> {
    }
}