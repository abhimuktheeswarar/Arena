package msa.rehearsal.round1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import msa.rehearsal.R;
import msa.rehearsal.round1.subround1.SubRound1Fragment;
import msa.rehearsal.round1.subround2.SubRound2Fragment;

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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SubRound1Fragment.newInstance(), SubRound1Fragment.class.getSimpleName()).commit();
    }

    @OnClick(R.id.fab)
    void onClickSwitchFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SubRound2Fragment.newInstance(), SubRound2Fragment.class.getSimpleName()).addToBackStack(SubRound2Fragment.class.getSimpleName()).commit();

    }

}
