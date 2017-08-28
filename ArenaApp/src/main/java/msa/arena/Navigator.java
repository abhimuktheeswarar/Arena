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
