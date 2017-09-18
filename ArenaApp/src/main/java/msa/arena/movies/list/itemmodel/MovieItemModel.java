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

package msa.arena.movies.list.itemmodel;

import android.widget.CompoundButton;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.github.davidmoten.rx2.util.Pair;

import msa.arena.R;
import msa.arena.movies.list.viewholder.MovieItemHolder;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@EpoxyModelClass(layout = R.layout.item_movie_2)
public abstract class MovieItemModel extends EpoxyModelWithHolder<MovieItemHolder> {

    @EpoxyAttribute
    String movieId, movieName;

    @EpoxyAttribute
    boolean isFavourite;

    @EpoxyAttribute(hash = false)
    MovieItemModelActionListener movieItemModelActionListener;

    private CompoundButton.OnCheckedChangeListener checkedChangeListener =
            new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            movieItemModelActionListener.onChangeFavorite(new Pair<>(movieId, b));
        }
            };

    @Override
    public void bind(MovieItemHolder holder) {
        super.bind(holder);
        holder.textView.setText(movieName);
        holder.checkBox.setChecked(isFavourite);
        holder.checkBox.setOnCheckedChangeListener(checkedChangeListener);
    }

    @Override
    public void unbind(MovieItemHolder holder) {
        super.unbind(holder);
        holder.checkBox.setOnCheckedChangeListener(null);
    }

    public interface MovieItemModelActionListener {

        void onChangeFavorite(Pair<String, Boolean> isFavorite);
  }
}
