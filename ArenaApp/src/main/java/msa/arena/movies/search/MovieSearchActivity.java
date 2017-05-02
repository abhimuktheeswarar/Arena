package msa.arena.movies.search;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import msa.arena.R;
import msa.arena.base.BaseActivity;
import msa.arena.injector.components.ApplicationComponent;
import msa.arena.injector.components.DaggerMovieComponent;
import msa.arena.injector.components.MovieComponent;

public class MovieSearchActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MovieComponent movieComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    protected void initializeInjector(ApplicationComponent applicationComponent) {
        movieComponent = DaggerMovieComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }


    @Override
    public MovieComponent getComponent() {
        return movieComponent;
    }
}
