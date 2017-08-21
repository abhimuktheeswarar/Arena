package msa.arena.epoxyplay;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import msa.arena.Navigator;
import msa.arena.R;
import msa.arena.base.BaseActivity;
import msa.arena.injector.components.ApplicationComponent;
import msa.arena.movies.list.MoviesActivity;

/**
 * Created by Abhimuktheeswarar on 30-05-2017.
 */

public class EpoxyPlayActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epoxyplay);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }


    @Override
    public Object getComponent() {
        return null;
    }

    @Override
    protected void initializeInjector(ApplicationComponent applicationComponent) {

    }
}
