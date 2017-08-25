package msa.arena.movies.list.listold;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import msa.arena.Navigator;
import msa.arena.R;
import msa.arena.base.old.BaseActivityOld;
import msa.arena.injector.components.ApplicationComponent;
import msa.arena.injector.components.DaggerMovieComponent;
import msa.arena.injector.components.MovieComponent;

public class MoviesActivityOld extends BaseActivityOld {

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  private MovieComponent movieComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(view -> Navigator.navigateToMovieSearchActivity(MoviesActivityOld.this));
  }

  @Override
  protected void initializeInjector(ApplicationComponent applicationComponent) {
    movieComponent =
            DaggerMovieComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .build();
  }

  @Override
  public MovieComponent getComponent() {
    return movieComponent;
  }
}
