package msa.arena.movies.search.spinner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import msa.arena.R;
import msa.arena.base.BaseActivity;
import msa.arena.movies.search.SearchViewModel;
import msa.arena.movies.searchold.MovieArrayAdapter;

public class SearchSpinnerActivity extends BaseActivity implements MovieArrayAdapter.MovieArrayAdapterInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;

    private SearchViewModel searchViewModel;

    private RxMovieArrayAdapter movieArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_spinner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchViewModel = getViewModelA(SearchViewModel.class);
        movieArrayAdapter = new RxMovieArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextView.setAdapter(movieArrayAdapter);
    }

    @Override
    protected void bind() {
        compositeDisposable.add(RxView.clicks(fab).subscribe(o -> autoCompleteTextView.showDropDown()));

        compositeDisposable.add(searchViewModel.getMovieListSearchObserver().observeOn(AndroidSchedulers.mainThread()).subscribe(linkedHashMapDataStateContainer -> {
            Log.d(TAG, "DateState = " + linkedHashMapDataStateContainer.getDataState());
            switch (linkedHashMapDataStateContainer.getDataState()) {
                case LOADING:
                    //materialProgressBar.setVisibility(View.VISIBLE);
                    //noinspection ConstantConditions
                    movieArrayAdapter.addAll(linkedHashMapDataStateContainer.getData());
                    break;
                case SUCCESS:
                    //materialProgressBar.setVisibility(View.INVISIBLE);
                    //noinspection ConstantConditions
                    movieArrayAdapter.addAll(linkedHashMapDataStateContainer.getData());
                    break;
                case COMPLETED:
                    //materialProgressBar.setVisibility(View.INVISIBLE);
                    //noinspection ConstantConditions
                    movieArrayAdapter.addAll(linkedHashMapDataStateContainer.getData());
                case ERROR:
                    //materialProgressBar.setVisibility(View.INVISIBLE);
                    //noinspection ConstantConditions
                    //movieArrayAdapter.addAll(linkedHashMapDataStateContainer.getData());
                    showToastMessage(linkedHashMapDataStateContainer.getMessage());
                    break;
                default:
                    //materialProgressBar.setVisibility(View.INVISIBLE);
                    break;
            }

        }));

        compositeDisposable.add(movieArrayAdapter.getQueryObservable().debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged().map(CharSequence::toString).subscribe(query -> searchViewModel.searchIt(query)));

        compositeDisposable.add(RxAutoCompleteTextView.itemClickEvents(autoCompleteTextView).map(adapterViewItemClickEvent -> adapterViewItemClickEvent.position()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer position) throws Exception {
                showToastMessage(searchViewModel.getMovieByIndex(position).getMovieName());
            }
        }));
    }

    @Override
    protected void unBind() {

    }

    @Override
    public void getMovieSuggestionsFromCloud(String query) {
        searchViewModel.searchIt(query);
    }
}
