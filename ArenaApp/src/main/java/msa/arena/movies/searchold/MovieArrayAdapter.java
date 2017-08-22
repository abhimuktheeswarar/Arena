package msa.arena.movies.searchold;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;

import msa.arena.injector.PerActivity;
import msa.domain.entities.Movie;

/**
 * Created by Abhimuktheeswarar on 03-05-2017.
 */
@PerActivity
public class MovieArrayAdapter extends ArrayAdapter<Movie> implements Filterable {

    private static final String TAG = MovieArrayAdapter.class.getSimpleName();
    private final MovieArrayAdapterInterface movieArrayAdapterInterface;
    private ArrayList<Movie> movieArrayList;
    private Filter filter =
            new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {}

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return super.convertResultToString(resultValue);
        }
            };

    MovieArrayAdapter(
            @NonNull Context context,
            @LayoutRes int resource,
            MovieArrayAdapterInterface movieArrayAdapterInterface) {
        super(context, resource);
        this.movieArrayAdapterInterface = movieArrayAdapterInterface;
        initialize();
    }

    private void initialize() {
        if (movieArrayList == null) movieArrayList = new ArrayList<>();
    }

    @Override
    public void add(@Nullable Movie object) {
        super.add(object);
        movieArrayList.add(object);
        //notifyDataSetChanged();
    }

    @Override
    public void addAll(@NonNull Collection<? extends Movie> collection) {
        movieArrayList.clear();
        movieArrayList.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return movieArrayList.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return movieArrayList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                //ArrayList<Movie> filterData = new ArrayList<>();
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    doTheQuery(constraint);
                }
                results.values = movieArrayList;
                if (movieArrayList != null) {
                    results.count = movieArrayList.size();
                } else {
                    results.count = 0;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    movieArrayList = (ArrayList<Movie>) results.values;
                    //notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                if (resultValue instanceof Movie) {
                    return ((Movie) resultValue).getMovieName();
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }

    private ArrayList<Movie> doTheQuery(CharSequence query) {
        movieArrayAdapterInterface.getMovieSuggestionsFromCloud(query.toString());
        return movieArrayList;
    }

    interface MovieArrayAdapterInterface {
        void getMovieSuggestionsFromCloud(String query);
  }
}
