package msa.arena.di.module;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import msa.arena.di.ArenaViewModelFactory;

/**
 * Created by Abhimuktheeswarar on 25-06-2017.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ArenaViewModelFactory factory);
}
