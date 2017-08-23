package msa.arena;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import msa.arena.base.BaseActivity;
import msa.arena.movies.search.searchmenu.SearchMenuActivity;
import msa.arena.movies.search.spinner.SearchSpinnerActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindViews({R.id.btn_search_menu, R.id.btn_search_spinner})
    List<AppCompatButton> appCompatButtonList;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void bind() {

        compositeDisposable.add(RxView.clicks(appCompatButtonList.get(0)).subscribe(o -> startActivity(new Intent(MainActivity.this, SearchMenuActivity.class))));

        compositeDisposable.add(RxView.clicks(appCompatButtonList.get(1)).subscribe(o -> startActivity(new Intent(MainActivity.this, SearchSpinnerActivity.class))));

        compositeDisposable.add(RxView.clicks(fab).subscribe(o -> Snackbar.make(fab, "Thank you for visiting Arena", Snackbar.LENGTH_LONG).setAction("Action", null).show()));

    }

    @Override
    protected void unBind() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
