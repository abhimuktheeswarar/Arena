package msa.arena.movies.list.itemmodel;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import msa.arena.R;
import msa.arena.movies.list.viewholder.MovieItemHolder;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@EpoxyModelClass(layout = R.layout.item_movie)
public abstract class MovieItemModel extends EpoxyModelWithHolder<MovieItemHolder> {

    @EpoxyAttribute
    public String movieId, movieName;

    @Override
    public void bind(MovieItemHolder holder) {
        super.bind(holder);
        holder.textView.setText(movieName);
    }

    @Override
    public void unbind(MovieItemHolder holder) {
        super.unbind(holder);
    }
}
