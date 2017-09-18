/*
 * Copyright 2017, Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package msa.arena.movies.search.spinner;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import msa.domain.entities.Movie;

/**
 * Created by Abhimuktheeswarar on 03-05-2017.
 */
public class RxMovieArrayAdapter extends ArrayAdapter<Movie> implements Filterable {

    private static final String TAG = RxMovieArrayAdapter.class.getSimpleName();
    private final PublishSubject<String> publishSubject;
    private ArrayList<Movie> movieArrayList;
    private Filter filter =
            new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    return null;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                }

                @Override
                public CharSequence convertResultToString(Object resultValue) {
                    return super.convertResultToString(resultValue);
                }
            };

    public RxMovieArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        publishSubject = PublishSubject.create();
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
        publishSubject.onNext(query.toString());
        return movieArrayList;
    }

    public Observable<String> getQueryObservable() {
        return publishSubject;
  }
}
