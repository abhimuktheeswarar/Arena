package msa.arena.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import msa.arena.movies.search.searchmenu.SearchMenuActivity;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract SearchMenuActivity contributeSearchMenuActivity();

}
