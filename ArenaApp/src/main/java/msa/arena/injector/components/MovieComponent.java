package msa.arena.injector.components;

import dagger.Component;
import msa.arena.injector.PerActivity;
import msa.arena.injector.modules.ActivityModule;
import msa.arena.injector.modules.MovieModule;
import msa.arena.movies.list.MoviesFragment;
import msa.arena.movies.search.MovieSearchFragment;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MovieModule.class})
public interface MovieComponent {

    void inject(MoviesFragment moviesFragment);

    void inject(MovieSearchFragment movieSearchFragment);
}
