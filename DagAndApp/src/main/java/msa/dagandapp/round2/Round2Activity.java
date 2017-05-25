package msa.dagandapp.round2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.AndroidInjection;
import msa.dagandapp.R;

public class Round2Activity extends AppCompatActivity implements Round2View {


    @Inject
    Lazy<Round2Presenter> round2PresenterLazy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
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
                round2PresenterLazy.get().load();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void loadData(int a) {
        Log.d(Round2Activity.class.getSimpleName(), "A = " + a);
        Toast.makeText(this, "Data = " + a, Toast.LENGTH_LONG).show();
    }
}
