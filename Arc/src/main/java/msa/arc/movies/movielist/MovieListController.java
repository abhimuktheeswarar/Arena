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

package msa.arc.movies.movielist;

import com.airbnb.epoxy.TypedEpoxyController;
import com.github.davidmoten.rx2.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import msa.domain.entities.Lce;
import msa.domain.entities.Movie;

/**
 * Created by Abhimuktheeswarar on 11-06-2017.
 */

public class MovieListController extends TypedEpoxyController<Lce<LinkedHashMap<String, Movie>>> implements MovieItemModel.MovieItemClickListener {

    private final BehaviorProcessor<Pair<String, Boolean>> movieBehaviorProcessor = BehaviorProcessor.create();

    private final BehaviorProcessor<Integer> integerBehaviorProcessor = BehaviorProcessor.create();


    void setMovies(Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) {
        setData(linkedHashMapLce);
    }


    @Override
    protected void buildModels(Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) {

        if (linkedHashMapLce.getData() != null) {
            for (Map.Entry<String, Movie> entry : linkedHashMapLce.getData().entrySet()) {
                Movie movie = entry.getValue();
                new MovieItemModel_().id(movie.getMovieId()).movieId(movie.getMovieId()).movieName(movie.getMovieName()).isFavorite(movie.isFavorite()).movieItemClickListener(this).addTo(this);
            }
        }
    }

    @Override
    public void onClickFavorite(String movieId, boolean isFavorite) {
        movieBehaviorProcessor.onNext(Pair.create(movieId, isFavorite));
    }

    Flowable<Pair<String, Boolean>> listenForFavorite() {
        return movieBehaviorProcessor;
    }
}
