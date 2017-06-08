package msa.arc.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import msa.arc.movies.MoviesActivity;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class, MovieModule.class})
    abstract MoviesActivity contributeMoviesActivity();
}
