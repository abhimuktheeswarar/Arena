package msa.arena.movies.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;

import com.msa.domain.entities.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import msa.arena.R;
import msa.arena.base.BaseEpoxyAdapter;
import msa.arena.base.BaseFragment;
import msa.arena.injector.components.MovieComponent;
import msa.arena.movies.MoviesItem;
import msa.arena.movies.MoviesView;
import msa.arena.utilities.RxSearch;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieSearchFragment extends BaseFragment implements MoviesView, MovieArrayAdapter.MovieArrayAdapterInterface {

    private static final String TAG = MovieSearchFragment.class.getSimpleName();
    final int EXPAND_MAX = 3;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.edit_suggestions)
    EditText editText;
    @BindView(R.id.recycler_movies)
    RecyclerView recyclerView_Movies;
    SearchView searchView;
    @Inject
    Lazy<MovieSearchPresenter> movieSearchPresenterLazy;
    ArrayAdapter<String> arrayAdapter;
    private MovieArrayAdapter movieArrayAdapter;
    private BaseEpoxyAdapter baseEpoxyAdapter;
    private SearchView.OnQueryTextListener textListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            movieSearchPresenterLazy.get().onSearchTypeTwo(newText);
            return true;
        }
    };

    //----------------------------------------------------------------------------------------------
    private Context mPopupContext;
    private ListPopupWindow listPopupWindow;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(TAG, "beforeTextChanged = " + s.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged = " + s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(TAG, "afterTextChanged = " + s.toString());
            if (s.length() <= 0) listPopupWindow.dismiss();
            movieSearchPresenterLazy.get().onSearchTypeTwo(s.toString());
        }
    };
    //private PassThroughClickListener mPassThroughClickListener;
    private ListAdapter mAdapter;
    private int mThreshold;
    private boolean mDropDownDismissedOnCompletion = true;
    private boolean mOpenBefore;
    private boolean mPopupCanBeUpdated = true;


    private void setUpMyAutoSugesTxt() {
        listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setAdapter(arrayAdapter);
        listPopupWindow.setAnchorView(editText);
        listPopupWindow.setModal(true);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_search, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_Movies.setLayoutManager(linearLayoutManager);
        baseEpoxyAdapter = new BaseEpoxyAdapter();
        recyclerView_Movies.setAdapter(baseEpoxyAdapter);
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        movieArrayAdapter = new MovieArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, this);
        autoCompleteTextView.setAdapter(movieArrayAdapter);
        setUpMyAutoSugesTxt();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(MovieComponent.class).inject(this);
        movieSearchPresenterLazy.get().setMoviesView(this);
        movieSearchPresenterLazy.get().initializePresenter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(textListener);
        //setupSearchView();
    }

    private void setupSearchView() {
        RxSearch.fromSearchView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(item -> item.length() > 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> movieSearchPresenterLazy.get().onSearch(query));
    }

    @Override
    public void onResume() {
        super.onResume();
        movieSearchPresenterLazy.get().onResume();
        //autoCompleteTextView.addTextChangedListener(textWatcher);
        //editText.addTextChangedListener(textWatcher);
    }

    @Override
    public void onPause() {
        movieSearchPresenterLazy.get().onPause();
        super.onPause();
    }

    @Override
    public void loadMovies(List<Movie> movies) {
        /*List<String> strings = new ArrayList<>();
        for (Movie moviesItem : movies) strings.add(moviesItem.getMovieName());
        arrayAdapter.clear();
        arrayAdapter.addAll(strings);
        listPopupWindow.show();*/
        movieArrayAdapter.addAll(movies);

    }

    @Override
    public void loadMovieItem(MoviesItem moviesItem) {
        baseEpoxyAdapter.addItem(moviesItem);
    }

    @Override
    public void loadMovieItem(List<MoviesItem> moviesItemList) {
        baseEpoxyAdapter.removeAllItems();
        //baseEpoxyAdapter.addItem(moviesItemList);
        List<String> strings = new ArrayList<>();
        for (MoviesItem moviesItem : moviesItemList) strings.add(moviesItem.movieName);


        autoCompleteTextView.enoughToFilter();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                autoCompleteTextView.showDropDown();
            }
        }, 500);


    }

    @Override
    public void clearMovieItems() {
        baseEpoxyAdapter.removeAllItems();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(String message) {
        showToastMessage(message);
    }

    @Override
    public void hideError() {

    }

    @Override
    public Context context() {
        return getActivity();
    }

    @OnClick(R.id.btn_show)
    void onClickShow() {
        autoCompleteTextView.showDropDown();
    }

    @Override
    public void getMovieSuggestionsFromCloud(String query) {
        movieSearchPresenterLazy.get().onSearchTypeTwo(query);
    }
}
