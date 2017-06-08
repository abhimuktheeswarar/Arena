package msa.arc.di.module;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import msa.arc.base.ArcViewModelFactory;
import msa.arc.base.BaseViewModel;
import msa.arc.di.ViewModelKey;
import msa.arc.movies.MoviesViewModel;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract BaseViewModel bindMoviesViewModel(MoviesViewModel moviesViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ArcViewModelFactory factory);
}
