package msa.rehearsal.injector.components;

import dagger.Component;
import msa.rehearsal.injector.PerActivity;
import msa.rehearsal.injector.modules.ActivityModule;
import msa.rehearsal.injector.modules.MovieModule;
import msa.rehearsal.round1.subround1_2.SubRound1_2Fragment;
import msa.rehearsal.round2.subround2_1.SubRound2_1Fragment;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MovieModule.class}
)
public interface MovieComponent {

    void inject(SubRound1_2Fragment subRound12Fragment);

    void inject(SubRound2_1Fragment subRound21Fragment);


}
