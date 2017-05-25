package msa.dagandapp.mydaggy;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import msa.dagandapp.round2.BasePresenter;
import msa.dagandapp.round2.BaseView;
import msa.dagandapp.round2.Round2Activity;
import msa.dagandapp.round2.Round2Presenter;

/**
 * Created by Abhimuktheeswarar on 25-05-2017.
 */

@Module(subcomponents = {Round2SubComponent.class})
abstract class Round2Module {

    @Provides
    static int provideFeatureView() {
        return 66;
    }

    @Binds
    @IntoMap
    @ActivityKey(Round2Activity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindRound2ActivityInjectorFactory(Round2SubComponent.Builder builder);

    @Binds
    abstract BaseView provideBaseView(Round2Activity round2Activity);

    /*@Provides
    BasePresenter providePresenter(int a) {
        return new Round2Presenter(a);
    }*/


}
