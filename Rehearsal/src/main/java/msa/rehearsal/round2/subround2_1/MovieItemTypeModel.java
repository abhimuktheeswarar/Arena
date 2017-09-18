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

package msa.rehearsal.round2.subround2_1;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import java.util.List;

import butterknife.BindView;
import msa.domain.entities.Movie;
import msa.rehearsal.R;
import msa.rehearsal.base.BaseEpoxyHolder;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

@EpoxyModelClass(layout = R.layout.item_movie_simple)
abstract class MovieItemTypeModel extends EpoxyModelWithHolder<MovieItemTypeModel.MovieItemHolder> {

    @EpoxyAttribute
    Movie movie;
    @EpoxyAttribute(hash = false)
    MovieItemTypeClickListener movieItemTypeClickListener;

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            movie.setFavorite(isChecked);
            if (movieItemTypeClickListener != null)
                movieItemTypeClickListener.onClickFavorite(movie);
        }
    };


    @Override
    public void bind(MovieItemHolder holder, List<Object> payloads) {
        super.bind(holder, payloads);
    }

    @Override
    public void bind(MovieItemHolder holder, EpoxyModel<?> previouslyBoundModel) {
        super.bind(holder, previouslyBoundModel);
    }

    @Override
    public void bind(MovieItemHolder holder) {
        super.bind(holder);
        holder.tV_MovieName.setText(movie.getMovieId());
        holder.checkBox.setChecked(movie.isFavorite());
        holder.checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        //holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public void unbind(MovieItemHolder holder) {
        super.unbind(holder);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.itemView.setOnClickListener(null);
    }

    @Override
    public boolean shouldSaveViewState() {
        return true;
    }

    interface MovieItemTypeClickListener {
        void onClickFavorite(Movie movie);
    }

    class MovieItemHolder extends BaseEpoxyHolder {

        @BindView(R.id.text_movie_name)
        TextView tV_MovieName;

        @BindView(R.id.checkbox_movie_favorite)
        CheckBox checkBox;

    }
}
