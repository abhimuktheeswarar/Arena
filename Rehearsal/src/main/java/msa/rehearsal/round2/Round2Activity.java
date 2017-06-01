package msa.rehearsal.round2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import msa.rehearsal.R;
import msa.rehearsal.base.BaseActivity;
import msa.rehearsal.injector.components.ApplicationComponent;
import msa.rehearsal.injector.components.DaggerMovieComponent;
import msa.rehearsal.injector.components.MovieComponent;
import msa.rehearsal.round2.subround2_1.SubRound2_1Fragment;

public class Round2Activity extends BaseActivity {

    private MovieComponent movieComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SubRound2_1Fragment.newInstance(), SubRound2_1Fragment.class.getSimpleName()).commit();
    }

    @Override
    protected void initializeInjector(ApplicationComponent applicationComponent) {
        movieComponent = DaggerMovieComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

    }

    @Override
    public Object getComponent() {
        return movieComponent;
    }
}
