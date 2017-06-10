package msa.arc.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import msa.arc.movies.MovieChildFragment;
import msa.arc.movies.MoviesFragment;
import msa.arc.movies.movielist.MovieListFragment;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MoviesFragment contributeMoviesFragment();

    @ContributesAndroidInjector
    abstract MovieChildFragment contributeMovieChildFragment();

    @ContributesAndroidInjector
    abstract MovieListFragment contributeMovieListFragment();
}