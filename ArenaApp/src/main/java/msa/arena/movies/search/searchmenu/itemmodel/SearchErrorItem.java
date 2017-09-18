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

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import msa.arena.R;
import msa.arena.movies.search.searchmenu.viewholder.SearchErrorItemHolder;

/**
 * Created by Abhimuktheeswarar on 19-07-2017.
 */

@EpoxyModelClass(layout = R.layout.item_search_error)
public abstract class SearchErrorItem extends EpoxyModelWithHolder<SearchErrorItemHolder> {

    @EpoxyAttribute
    String errorMessage;

    @Override
    public void bind(SearchErrorItemHolder holder) {
        super.bind(holder);
        holder.textView.setText(errorMessage);
    }
}
