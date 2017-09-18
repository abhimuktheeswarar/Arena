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

package msa.arena.movies.list.viewholder;

import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import msa.arena.R;
import msa.arena.base.BaseEpoxyHolder;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public class MovieItemHolder extends BaseEpoxyHolder {

    @BindView(R.id.text_movie_name)
    public TextView textView;

    @BindView(R.id.check_movie_favorite)
    public CheckBox checkBox;
}
