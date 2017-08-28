package msa.arena.movies;

import android.widget.TextView;

import butterknife.BindView;
import msa.arena.R;
import msa.arena.base.BaseEpoxyHolder;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
class MoviesHolder extends BaseEpoxyHolder {

    @BindView(R.id.text_movie_name)
    TextView textView;
}
