package msa.arena;

import android.content.Context;
import android.content.Intent;

import msa.arena.movies.list.MoviesActivityOld;
import msa.arena.movies.searchold.MovieSearchActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public class Navigator {

    static void navigateToMoviesActivity(Context context) {
        Intent intentToLaunch = new Intent(context, MoviesActivityOld.class);
        context.startActivity(intentToLaunch);
    }

    public static void navigateToMovieSearchActivity(Context context) {
        Intent intentToLaunch = new Intent(context, MovieSearchActivity.class);
        context.startActivity(intentToLaunch);
    }
}
