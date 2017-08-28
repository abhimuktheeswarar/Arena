package msa.arena.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import msa.arena.di.ArenaViewModelFactory;
import msa.arena.di.ViewModelKey;
import msa.arena.movies.list.MovieListViewModel;
import msa.arena.movies.search.SearchViewModel;

/**
 * Created by Abhimuktheeswarar on 25-06-2017.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ArenaViewModelFactory factory);

    //----------------------------------------------------------------------------------------------

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    abstract ViewModel bindMovieListViewModel(MovieListViewModel movieListViewModel);
}
