package msa.arena.movies.list.viewholder;

import android.widget.TextView;

import butterknife.BindView;
import msa.arena.R;
import msa.arena.base.old.BaseEpoxyHolder;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public class MovieItemHolder extends BaseEpoxyHolder {

    @BindView(R.id.text_movie_name)
    public TextView textView;
}
