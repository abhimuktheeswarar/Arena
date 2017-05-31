package msa.dagandapp.mydaggy;

import android.app.Activity;

import dagger.android.AndroidInjector;
import msa.dagandapp.androiddaggy.Round2SubComponent;
import msa.dagandapp.round2.BaseView;
import msa.dagandapp.round2.Round2Activity;

/**
 * Created by Abhimuktheeswarar on 25-05-2017.
 */

//@Module(subcomponents = {Round2SubComponent.class})
abstract class Round2Module {

    //@Provides
    static int provideFeatureView() {
        return 66;
    }

    //@Binds
    //@IntoMap
    //@ActivityKey(Round2Activity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindRound2ActivityInjectorFactory(Round2SubComponent.Builder builder);

    //@Binds
    abstract BaseView provideBaseView(Round2Activity round2Activity);

    /*@Provides
    BasePresenter providePresenter(int a) {
        return new Round2Presenter(a);
    }*/


}
