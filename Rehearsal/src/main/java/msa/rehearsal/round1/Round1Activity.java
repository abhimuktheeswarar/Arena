package msa.rehearsal.round1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import msa.rehearsal.R;
import msa.rehearsal.round1.subround1_1.SubRound1_1Fragment;
import msa.rehearsal.round1.subround1_2.SubRound1_2Fragment;

public class Round1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round1);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SubRound1_1Fragment.newInstance(), SubRound1_1Fragment.class.getSimpleName()).commit();
    }

    @OnClick(R.id.fab)
    void onClickSwitchFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SubRound1_2Fragment.newInstance(), SubRound1_2Fragment.class.getSimpleName()).addToBackStack(SubRound1_2Fragment.class.getSimpleName()).commit();

    }

}
