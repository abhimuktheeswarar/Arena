package msa.arena.movies.search;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import msa.arena.injector.modules.MovieModule;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

@Subcomponent(modules = MovieModule.class)
public interface MovieSubComponent extends AndroidInjector<MovieSearchFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MovieSearchFragment> {
    }
}
