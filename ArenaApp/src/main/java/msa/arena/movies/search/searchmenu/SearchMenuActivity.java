package msa.arena.movies.search.searchmenu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import msa.arena.R;
import msa.arena.base.BaseActivity;
import msa.arena.movies.search.SearchViewModel;

public class SearchMenuActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerView;

    SearchView searchView;

    private SearchViewModel searchViewModel;

    private SearchListController searchListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchViewModel = getViewModelA(SearchViewModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchListController = new SearchListController();
        recyclerView.setAdapter(searchListController.getAdapter());
        recyclerView.setItemAnimator(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint(getString(R.string.hint_search));
        bindSearchView();
        return true;
    }

    @Override
    protected void bind() {

        compositeDisposable.add(RxView.clicks(fab).subscribe(o -> Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()));

        compositeDisposable.add(searchViewModel.getMovieSearchObserver().observeOn(AndroidSchedulers.mainThread()).subscribe(linkedHashMapDataStateContainer -> {
            Log.d(TAG, "DateState = " + linkedHashMapDataStateContainer.getDataState());
            switch (linkedHashMapDataStateContainer.getDataState()) {
                case LOADING:
                    //materialProgressBar.setVisibility(View.VISIBLE);
                    searchListController.setSearchResultData(linkedHashMapDataStateContainer);
                    break;
                case SUCCESS:
                    //materialProgressBar.setVisibility(View.INVISIBLE);
                    searchListController.setSearchResultData(linkedHashMapDataStateContainer);
                    break;
                case COMPLETED:
                    //materialProgressBar.setVisibility(View.INVISIBLE);
                    searchListController.setSearchResultData(linkedHashMapDataStateContainer);
                case ERROR:
                    //materialProgressBar.setVisibility(View.INVISIBLE);
                    searchListController.setSearchResultData(linkedHashMapDataStateContainer);
                    break;
                default:
                    //materialProgressBar.setVisibility(View.INVISIBLE);
                    break;
            }

        }));

    }

    @Override
    protected void unBind() {
    }

    private void bindSearchView() {

        Log.d(TAG, "bindSearchView");

        compositeDisposable.add(RxSearchView.queryTextChanges(searchView).skipInitialValue().debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged().map(CharSequence::toString).subscribe(query -> searchViewModel.searchIt(query)));

        searchView.requestFocus();
    }

}
