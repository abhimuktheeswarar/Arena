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

package msa.arena.movies.list;

import android.util.Log;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.github.davidmoten.rx2.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import msa.arena.common.item.ErrorItemModel_;
import msa.arena.common.item.LoadMoreItemModel_;
import msa.arena.movies.list.itemmodel.MovieItemModel;
import msa.arena.movies.list.itemmodel.MovieItemModel_;
import msa.arena.movies.search.searchmenu.itemmodel.SearchItem;
import msa.domain.entities.Movie;
import msa.domain.holder.datastate.DataState;
import msa.domain.holder.datastate.DataStateContainer;

/**
 * Created by Abhimuktheeswarar on 19-07-2017.
 */

class MovieListController extends TypedEpoxyController<DataStateContainer<LinkedHashMap<String, Movie>>> implements SearchItem.SearchItemActionListener, MovieItemModel.MovieItemModelActionListener {

    private static final String TAG = MovieListController.class.getSimpleName();

    private final PublishSubject<Pair<String, Boolean>> publishSubject;

    @AutoModel
    LoadMoreItemModel_ loadMoreItemModel_F, loadMoreItemModel;

    @AutoModel
    ErrorItemModel_ errorItemModel_F, errorItemModel;

    MovieListController() {
        publishSubject = PublishSubject.create();
    }

    void setMovies(DataStateContainer<LinkedHashMap<String, Movie>> searchResultData) {
        setData(searchResultData);
    }

    @Override
    protected void buildModels(DataStateContainer<LinkedHashMap<String, Movie>> movies) {

        Log.d(TAG, "DataState = " + movies.getDataState() + " | is data available = " + (movies.getData() != null));

        loadMoreItemModel_F.isFullHeight(true).addIf(movies.getDataState() == DataState.INITIALIZE, this);

        if (movies.getData() != null && movies.getData().size() > 0) {
            for (Map.Entry<String, Movie> entry : movies.getData().entrySet()) {
                Movie movie = entry.getValue();

                new MovieItemModel_()
                        .id(movie.getMovieId())
                        .movieId(movie.getMovieId())
                        .movieName(movie.getMovieName())
                        .isFavourite(movie.isFavorite())
                        .movieItemModelActionListener(this)
                        .addTo(this);
            }
        }
        if (movies.getDataState() == DataState.LOADING && movies.getData().size() > 0)
            loadMoreItemModel.isFullHeight(false).addTo(this);
        else if (movies.getDataState() == DataState.ERROR && movies.getData().size() > 0)
            errorItemModel.isFullHeight(false).errorMessage(movies.getMessage()).addTo(this);
        else if (movies.getDataState() == DataState.ERROR && movies.getData().size() == 0)
            errorItemModel_F.isFullHeight(true).errorMessage(movies.getMessage()).addTo(this);
    }

    @Override
    public void onChangeFavorite(Pair<String, Boolean> isFavorite) {
        publishSubject.onNext(isFavorite);
    }

    Observable<Pair<String, Boolean>> getFavoriteChangeObservable() {
        return publishSubject;
    }
}
