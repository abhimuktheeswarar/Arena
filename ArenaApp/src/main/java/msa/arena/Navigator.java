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

package msa.arena;

import android.content.Context;
import android.content.Intent;

import msa.arena.movies.list.MovieListActivity;
import msa.arena.movies.search.searchmenu.SearchMenuActivity;
import msa.arena.movies.search.spinner.SearchSpinnerActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public class Navigator {

    static void navigateToSearchMenuActivity(Context context) {
        Intent intentToLaunch = new Intent(context, SearchMenuActivity.class);
        context.startActivity(intentToLaunch);
    }

    public static void navigateToSearchSpinnerActivity(Context context) {
        Intent intentToLaunch = new Intent(context, SearchSpinnerActivity.class);
        context.startActivity(intentToLaunch);
    }

    public static void navigateToMovieListActivity(Context context) {
        Intent intentToLaunch = new Intent(context, MovieListActivity.class);
        context.startActivity(intentToLaunch);
    }
}
