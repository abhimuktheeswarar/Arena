package msa.arc.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import msa.arc.base.ArcViewModelFactory;
import msa.arc.di.ViewModelKey;
import msa.arc.movies.MovieChildViewModel;
import msa.arc.movies.MoviesViewModel;
import msa.arc.movies.movielist.MovieListViewModel;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract ViewModel bindMoviesViewModel(MoviesViewModel moviesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieChildViewModel.class)
    abstract ViewModel bindMovieChildViewModel(MovieChildViewModel movieChildViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    abstract ViewModel bindMovieListViewModel(MovieListViewModel movieListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ArcViewModelFactory factory);
}
