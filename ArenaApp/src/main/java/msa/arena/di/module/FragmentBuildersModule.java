package msa.arena.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import msa.arena.movies.search.searchmenu.SearchMenuDummyFragment;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SearchMenuDummyFragment contributeSearchMenuDummyFragment();


}