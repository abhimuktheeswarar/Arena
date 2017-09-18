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

package msa.arena.movies.search.searchmenu.itemmodel;

import android.view.View;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import msa.arena.R;
import msa.arena.movies.search.searchmenu.viewholder.SearchItemHolder;

/**
 * Created by Abhimuktheeswarar on 19-07-2017.
 */
@EpoxyModelClass(layout = R.layout.item_search)
public abstract class SearchItem extends EpoxyModelWithHolder<SearchItemHolder> {

    @EpoxyAttribute
    String movieId, movieName, queryText;

    @EpoxyAttribute(hash = false)
    SearchItemActionListener searchItemActionListener;

    private View.OnClickListener onClickListener =
            new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
            };

    @Override
    public void bind(SearchItemHolder holder) {
        super.bind(holder);
        holder.textView.setText(movieName);
        holder.frameLayout.setOnClickListener(onClickListener);
    }

    @Override
    public void unbind(SearchItemHolder holder) {
        super.unbind(holder);
        holder.frameLayout.setOnClickListener(null);
    }

    public interface SearchItemActionListener {}
}
