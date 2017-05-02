package msa.arena;

import android.content.Context;
import android.content.Intent;

import msa.arena.movies.list.MoviesActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class Navigator {

    public static void navigateToMoviesActivity(Context context) {
        Intent intentToLaunch = new Intent(context, MoviesActivity.class);
        context.startActivity(intentToLaunch);
    }
}
