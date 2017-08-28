package msa.arena;

import android.content.Context;
import android.content.Intent;

import msa.arena.constraint.ConstraintActivity;
import msa.arena.movies.list.MoviesActivity;
import msa.arena.movies.search.MovieSearchActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public class Navigator {

    static void navigateToMoviesActivity(Context context) {
        Intent intentToLaunch = new Intent(context, MoviesActivity.class);
        context.startActivity(intentToLaunch);
    }

    public static void navigateToMovieSearchActivity(Context context) {
        Intent intentToLaunch = new Intent(context, MovieSearchActivity.class);
        context.startActivity(intentToLaunch);
    }

    public static void navigateToConstraintActivity(Context context) {
        Intent intentToLaunch = new Intent(context, ConstraintActivity.class);
        context.startActivity(intentToLaunch);
  }
}
