package msa.arc.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import msa.arc.movies.MoviesActivity;
import msa.arc.movies.movielist.MovieListActivity;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract MoviesActivity contributeMoviesActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract MovieListActivity contributeMovieListActivity();
}
