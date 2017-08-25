package msa.arena.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import msa.arena.MainActivity;
import msa.arena.movies.list.MovieListActivity;
import msa.arena.movies.search.searchmenu.SearchMenuActivity;
import msa.arena.movies.search.spinner.SearchSpinnerActivity;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract SearchMenuActivity contributeSearchMenuActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract SearchSpinnerActivity contributeSearchSpinnerActivity();


    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract MovieListActivity contributeMovieListActivity();

}
