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

package msa.arena.movies.search.searchmenu;

import android.util.Log;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;

import java.util.LinkedHashMap;
import java.util.Map;

import msa.arena.movies.search.searchmenu.itemmodel.SearchErrorItem_;
import msa.arena.movies.search.searchmenu.itemmodel.SearchItem;
import msa.arena.movies.search.searchmenu.itemmodel.SearchItem_;
import msa.domain.entities.Movie;
import msa.domain.holder.datastate.DataState;
import msa.domain.holder.datastate.DataStateContainer;

/**
 * Created by Abhimuktheeswarar on 19-07-2017.
 */
class SearchListController
        extends TypedEpoxyController<DataStateContainer<LinkedHashMap<String, Movie>>>
        implements SearchItem.SearchItemActionListener {

    private static final String TAG = SearchListController.class.getSimpleName();

    @AutoModel
    SearchErrorItem_ searchErrorItem;

    SearchListController() {
    }

    void setSearchResultData(DataStateContainer<LinkedHashMap<String, Movie>> searchResultData) {
        setData(searchResultData);
    }

    @Override
    protected void buildModels(DataStateContainer<LinkedHashMap<String, Movie>> searchResultData) {

        Log.d(TAG, "DateState = " + searchResultData.getDataState());

        if (searchResultData.getData() != null
                && (searchResultData.getDataState() == DataState.SUCCESS
                || searchResultData.getDataState() == DataState.LOADING)) {

            for (Map.Entry<String, Movie> entry : searchResultData.getData().entrySet()) {

                Movie searchResult = entry.getValue();

                new SearchItem_()
                        .id(searchResult.getMovieId())
                        .movieId(searchResult.getMovieId())
                        .movieName(searchResult.getMovieName())
                        .searchItemActionListener(this)
                        .addTo(this);
            }
        } else if (searchResultData.getDataState() == DataState.ERROR) {
            searchErrorItem.errorMessage(searchResultData.getMessage()).addTo(this);
        }
  }
}
